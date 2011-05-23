/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.velocity;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

import com.corona.logging.LogFactory;

/**
 * <p>This Velocity log is used to log Velocity logging </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Log implements LogChute {

	/**
	 * the logger name
	 */
	public static final String RUNTIME_LOG_LOGGER = "runtime.log.logsystem.logger";
	
	/**
	 * the logger
	 */
	private com.corona.logging.Log logger = null;
	
	/**
	 * {@inheritDoc}
	 * @see org.apache.velocity.runtime.log.LogChute#init(org.apache.velocity.runtime.RuntimeServices)
	 */
	@Override
	public void init(final RuntimeServices services) throws Exception {
		
		String name = (String) services.getProperty(RUNTIME_LOG_LOGGER);
		if (name != null) {
			this.logger = LogFactory.getLog(name);
		} else {
			this.logger = LogFactory.getLog(Log.class);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.apache.velocity.runtime.log.LogChute#isLevelEnabled(int)
	 */
	@Override
	public boolean isLevelEnabled(final int level) {
		
		switch (level) {
			case LogChute.ERROR_ID:
				return this.logger.isErrorEnabled();
				
			case LogChute.WARN_ID:
				return this.logger.isWarnEnabled();
				
			case LogChute.INFO_ID:
				return this.logger.isInfoEnabled();
				
			case LogChute.DEBUG_ID:
				return this.logger.isDebugEnabled();
				
			default:
				return this.logger.isTraceEnabled();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.apache.velocity.runtime.log.LogChute#log(int, java.lang.String)
	 */
	@Override
	public void log(final int level, final String message) {
		
		switch (level) {
			case LogChute.ERROR_ID:
				this.logger.error(message);
				break;
				
			case LogChute.WARN_ID:
				this.logger.warn(message);
				break;
				
			case LogChute.INFO_ID:
				this.logger.info(message);
				break;
				
			case LogChute.DEBUG_ID:
				this.logger.debug(message);
				break;
				
			default:
				this.logger.trace(message);
				break;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.apache.velocity.runtime.log.LogChute#log(int, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(final int level, final String message, final Throwable cause) {
		
		switch (level) {
			case LogChute.ERROR_ID:
				this.logger.error(message, cause);
				break;
				
			case LogChute.WARN_ID:
				this.logger.warn(message, cause);
				break;
				
			case LogChute.INFO_ID:
				this.logger.info(message, cause);
				break;

			case LogChute.DEBUG_ID:
				this.logger.debug(message, cause);
				break;

			default:
				this.logger.trace(message, cause);
				break;
		}
	}
}
