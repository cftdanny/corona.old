/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.lang.reflect.Field;

import org.slf4j.impl.JDK14LoggerAdapter;

/**
 * <p>This log manager will configure JDK logging </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JdkLogManager implements LogManager {

	/**
	 * {@inheritDoc}
	 * @see com.corona.logging.LogManager#configure(java.lang.String)
	 */
	@Override
	public void configure(final String resource) {
		
		java.util.logging.LogManager logManager = java.util.logging.LogManager.getLogManager();
		try {
			logManager.readConfiguration(JdkLogManager.class.getResourceAsStream(resource));
		} catch (Exception e) {
			logManager.getLogger(JdkLogManager.class.getName()).warning(
					"Fail to configure jdk logging because error: " + e.getMessage()
			);
		}
		
		try {
			Field field = JDK14LoggerAdapter.class.getDeclaredField("SELF");
			field.setAccessible(true);
			field.set(null, Log.class.getName());
		} catch (Exception e) {
			logManager.getLogger(JdkLogManager.class.getName()).warning(
					"Fail to set JDK14LoggerAdapter SELF field because error: " + e.getMessage()
			);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.logging.LogManager#reset()
	 */
	@Override
	public void reset() {
		java.util.logging.LogManager.getLogManager().reset();
	}
}
