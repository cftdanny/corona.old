/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm;

import java.util.List;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Process {

	/**
	 * @return the parent process or <code>null</code> if it is main process
	 */
	Process getParent();
	
	/**
	 * @return the process id
	 */
	Long getId();
	
	/**
	 * @return the context
	 */
	Context getContext();
	
	/**
	 * @return whether process is running
	 */
	boolean isActive();
	
	/**
	 * <p>Process to next step. </p>
	 * 
	 * @throws ProcessException if fail to signal process
	 */
	void signal() throws ProcessException;
	
	/**
	 * @return the current state of process 
	 */
	ActiveState getActiveState();
	
	/**
	 * <p>Suspend the process, can not change any data of process before resume process. </p>
	 *  
	 * @throws ProcessException if fail to suspend this process
	 */
	void suspend() throws ProcessException;
	
	/**
	 * <p>Resume the process, allow process 
	 * 
	 * @throws ProcessException if fail to resume this process
	 */
	void resume() throws ProcessException;
	
	/**
	 * <p>Cancel this process and mark this process is just cancelled. </p>
	 *  
	 * @throws ProcessException if fail to cancel this process
	 */
	void cancel() throws ProcessException;
	
	/**
	 * <p>Try to delete this process from persisted state, for example database. </p>
	 * 
	 * @throws ProcessException if fail to delete this process
	 */
	void delete() throws ProcessException;
	
	/**
	 * @return all child processes
	 */
	List<Process> getChildren();
}
