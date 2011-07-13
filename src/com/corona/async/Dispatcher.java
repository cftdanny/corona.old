/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.corona.context.ContextManager;
import com.corona.context.annotation.Transactional;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.Transaction;
import com.corona.data.TransactionManager;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This dispatcher class is used to execute asynchronous job method in component </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Dispatcher implements Job {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(Dispatcher.class);
	
	/**
	 * @param context the job execution context from Quartz
	 * @return the job descriptor
	 */
	private JobDescriptor getJobDescriptor(final JobExecutionContext context) {
		return (JobDescriptor) context.getJobDetail().getJobDataMap().get(JobDescriptor.class.getName());
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {

		// find job descriptor to build job execution environment
		JobDescriptor descriptor = this.getJobDescriptor(context);
		
		// create context manager factory in order to execute job in asynchronous method
		ContextManager contextManager = descriptor.getContextManagerFactory().create();
		
		// create component with asynchronous job method
		Object component = contextManager.get(descriptor.getType(), descriptor.getName());
		
		// execute asynchronous job method with data store in Quartz job context
		try {
			if (descriptor.getMethod().isAnnotationPresent(Transactional.class)) {
				this.execute(contextManager, component, descriptor);
			} else {
				descriptor.getMethod().invoke(component, descriptor.getArguments());
			}
		} catch (Exception e) {
			
			this.logger.error("Fail to execute method [{0}] with Quartz job", e, descriptor.getMethod().getName());
			throw new JobExecutionException(
					"Fail to execute method " + descriptor.getMethod().getName() + " with Quartz job", e
			);
		}

		// close context manager
		try {
			contextManager.close();
		} catch (Exception e) {
			
			this.logger.error("Fail to close created context manager in Quartz job", e);
			throw new JobExecutionException("Fail to close created context manager in Quartz job", e);
		}
	}

	/**
	 * @param contextManager the current context manager factory
	 * @param component the component
	 * @param descriptor the job descriptor
	 * @throws AsyncException if fail to execute job
	 */
	private void execute(
			final ContextManager contextManager, final Object component, final JobDescriptor descriptor
	) throws AsyncException {
		
		// open default connection manager from context container
		ConnectionManager connectionManager = null;
		try {
			connectionManager = this.getConnectionManager(contextManager);
		} catch (Exception e) {
			throw new AsyncException("Fail to get default connection manager, maybe is not registered", e);
		}

		// try to get container managed transaction first, if can'n, get connection manager's transaction
		Transaction transaction = this.getTransaction(contextManager);
		if (transaction == null) {
			transaction = connectionManager.getTransaction();
		}

		// try to start transaction, if can't, throw exception and close connection manager
		try {
			transaction.begin();
		} catch (Exception e) {
			
			// fail start transaction, will close connection manager
			try {
				connectionManager.close();
			} catch (Exception e1) {
				throw new AsyncException("Fail to close default connection manager", e);
			}
			throw new AsyncException("Fail to start transaction before invoke producer method", e);
		}
		
		// execute producer business logic method in transaction
		try {
			descriptor.getMethod().invoke(component, descriptor.getArguments());
			if (!transaction.getRollbackOnly()) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		} catch (Throwable e) {
			
			try {
				transaction.rollback();
			} catch (Exception ex) {
				this.logger.warn("Fail to roll back , but do not know how to handle this case, just ignore", e);
			}
			
			this.logger.error(
					"Fail to invoke method [{0}] within transaction, will roll back", e, descriptor.getMethod()
			);
			throw new AsyncException(
					"Fail to invoke method [{0}] within transaction, will roll back", e, descriptor.getMethod()
			);
			
		} finally {
			
			// try to close connection manager, if error, just skip it
			try {
				connectionManager.close();
			} catch (Exception e) {
				this.logger.warn(
						"Fail to close connection manager, but changed data has been commit or roll back", e
				);
			}
		}
	}

	/**
	 * @param contextManager the current context manager
	 * @return the default connection manager
	 * @throws Exception if fail to open connection manager
	 */
	private ConnectionManager getConnectionManager(final ContextManager contextManager) throws Exception {
		return contextManager.get(ConnectionManagerFactory.class).open();
	}
	
	/**
	 * @param contextManager the current context manager
	 * @return the container managed transaction
	 */
	private Transaction getTransaction(final ContextManager contextManager) {

		TransactionManager transactionManager = contextManager.get(TransactionManager.class);
		return (transactionManager != null) ? transactionManager.getTransaction() : null;
	}
}
