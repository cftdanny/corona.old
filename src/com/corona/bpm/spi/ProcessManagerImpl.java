/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.corona.bpm.Process;
import com.corona.bpm.ProcessException;
import com.corona.bpm.ProcessManager;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProcessManagerImpl implements ProcessManager {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ProcessManagerImpl.class);
	
	/**
	 * all definitions
	 */
	private Map<String, Definition> definitions = new HashMap<String, Definition>();
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#install(java.io.InputStream)
	 */
	@Override
	public void install(final InputStream stream) throws ProcessException {
		
		Definition definition = new Definition(stream);
		if (!this.definitions.containsKey(definition.getId())) {
			this.definitions.put(definition.getId(), definition);
		} else {
			
			this.logger.error("Process [{0}] already installed, can't load it again", definition.getId());
			throw new ProcessException(
					"Process [{0}] already installed, can't load it again", definition.getId()
			);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#install(java.lang.String)
	 */
	@Override
	public void install(final String resource) throws ProcessException {
		
		try {
			this.install(this.getClass().getClassLoader().getResourceAsStream(resource));
		} catch (Exception e) {
			
			this.logger.error("Fail to install process definition by resource file [{0}]", e, resource);
			throw new ProcessException(
					"Fail to install process definition by resource file [{0}]", e, resource
			);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#createProcess(java.lang.String)
	 */
	@Override
	public Process createProcess(final String name) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#getActiveProcess(java.lang.Long)
	 */
	@Override
	public Process getActiveProcess(Long id) throws ProcessException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#getActiveProcesses(java.lang.String)
	 */
	@Override
	public List<Process> getActiveProcesses(String name) throws ProcessException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#getActiveProcesses()
	 */
	@Override
	public List<Process> getActiveProcesses() throws ProcessException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#getProcess(java.lang.Long)
	 */
	@Override
	public Process getProcess(Long id) throws ProcessException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#getProcesses(java.lang.String)
	 */
	@Override
	public List<Process> getProcesses(String name) throws ProcessException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.ProcessManager#getProcesses()
	 */
	@Override
	public List<Process> getProcesses() throws ProcessException {
		return null;
	}

}
