/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import com.corona.context.ContextManager;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;
import com.corona.servlet.annotation.Excel;

/**
 * <p>This producer is used to create HTTP response by FreeMaker and annotated method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ExcelProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ExcelProducer.class);
	
	/**
	 * the method to create PDF document with producer result
	 */
	private String methodName;
	
	/**
	 * @param key the component key
	 * @param method the method that is annotated with {@link FreeMaker}
	 */
	public ExcelProducer(final Key<?> key, final DecoratedMethod method) {
		super(key, method);
		this.methodName = method.getMethod().getAnnotation(Excel.class).value();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, javax.servlet.http.HttpServletResponse, java.io.OutputStream, 
	 * 	java.lang.Object, java.lang.Object
	 * )
	 */
	@Override
	public void produce(
			final ContextManager contextManager, final HttpServletResponse response, final OutputStream out,
			final Object component, final Object data) throws ProduceException {

		// find component and the method that is used to create PDF document
		Method method = null;
		try {
			if (data != null) {
				method = component.getClass().getMethod(this.methodName, WritableWorkbook.class, data.getClass());
			} else {
				method = component.getClass().getMethod(this.methodName, WritableWorkbook.class);
			}
		} catch (Throwable e) {
			
			this.logger.error("Method [{0}] to create Microsoft EXCL in component [{1}] does not exist", 
					e, this.methodName, this.getKey()
			);
			throw new ProduceException("Method [{0}] to create Microsoft EXCL in component [{1}] does not exist", 
					e, this.methodName, this.getKey()
			);
		}
		
		// execute method to create PDF document and send to browser by HTTP
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(out);
		} catch (Exception e) {
			
			this.logger.error("Fail to create Microsoft EXCEL workbook by response", e);
			throw new ProduceException("Fail to create Microsoft EXCEL workbook by response", e);
		}
		
		try {
			if (data != null) {
				method.invoke(component, workbook, data);
			} else {
				method.invoke(component, workbook);
			}
		} catch (Throwable e) {
			
			this.logger.error("Fail when invoke method [{0}] of component [{1}] to create PDF", 
					e, this.methodName, this.getKey()
			);
			throw new ProduceException("Fail when invoke method [{0}] of component [{1}] to create PDF", 
					e, this.methodName, this.getKey()
			);
		}

		// set content type if it is not set yet
		if (response.getContentType() == null) {
			response.setContentType("application/vnd.ms-excel");
		}
		
		// write content to HTTP response stream
		try {
			workbook.write();
		} catch (IOException e) {
			
			this.logger.error(
					"Fail to write Microsoft EXCEL created by method [{0)] to response stream", e, method
			);
			throw new ProduceException(
					"Fail to write Microsoft EXCEL created by method [{0)] to response stream", e, method
			);
		} finally {
			
			try {
				workbook.close();
			} catch (Exception e) {
				
				this.logger.error(
						"Fail to close workbook that is created by method [{0)]", e, method
				);
				throw new ProduceException(
						"Fail to close workbook that is created by method [{0)]", e, method
				);
			}
		}
	}
}
