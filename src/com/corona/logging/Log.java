/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import org.slf4j.Logger;

/**
 * <p>This logger logs system runtime information by message, values of arguments and thrown exceptions. 
 * The logger provides 4 levels of logging: </p>
 * 
 * <ul>
 * 	<li>debug	: logs runtime information for developers in order to verify whether logic is OK. </li>
 * 	<li>info	: logs information to info something happens to users and developers. </li>
 * 	<li>warn	: logs warning information to users to indicate something needs notice. </li>
 * 	<li>error	: logs error happens to users and indicates something is wrong. </li>
 * </ul>
 * 
 * <p>Usually, we can create logger by {@link LogFactory} with logger name and class. For example:
 * <pre>{@code
 * private static final Log logger = LogFactory.getLog(Test.class);
 * ....
 * logger.info("The logging has been created successfully!");
 * }</pre>
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @since 1.0, 8/16/2010
 */
public class Log {

	/**
	 * the delegated logger
	 */
	private Logger logger;
	
	/**
	 * <p>create logger with the delegated logger. This constructor is called in {@link LogFactory} currently. </p>
	 *  
	 * @param logger the delegated logger
	 */
	protected Log(final Logger logger) {
		this.logger = logger;
	}

	/**
	 * @return whether trace level logging is enabled
	 */
	public boolean isTraceEnabled() {
		return this.logger.isTraceEnabled();
	}
	
	/**
	 * <p>log TRACE level message with pattern and values of arguments. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog("name");
	 * ...
	 * logger.trace("Application starts at :{0}", new Date());
	 * }
	 * </pre>
	 * </p>
	 *  
	 * @param pattern the pattern that is used to formatted to logging message with arguments.
	 * @param args the values of arguments for parameter pattern.
	 */
	public void trace(final String pattern, final Object... args) {
		
		if (this.logger.isTraceEnabled()) {
			this.logger.trace(LogListeners.log(pattern, args));
		}
	}

	/**
	 * <p>log TRACE level message with pattern, values of arguments and thrown exception. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog("name");
	 * ...
	 * try {
	 * 	...
	 * } catch (Exception e) {
	 * 	logger.trace("Something minor error happens at :{0}", new Date());
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param pattern the pattern that is used to formatted to logging message with arguments 
	 * @param e the thrown exception
	 * @param args the values of arguments for parameter pattern.
	 */
	public void trace(final String pattern, final Throwable e, final Object... args) {
		
		if (this.logger.isTraceEnabled()) {
			this.logger.trace(LogListeners.log(pattern, e, args), e);
		}
	}

	/**
	 * @return whether debug level logging is enabled
	 */
	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	/**
	 * <p>log DEBUG level message with pattern and values of arguments. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog("name");
	 * ...
	 * logger.debug("Application starts at :{0}", new Date());
	 * }
	 * </pre>
	 * </p>
	 *  
	 * @param pattern the pattern that is used to formatted to logging message with arguments.
	 * @param args the values of arguments for parameter pattern.
	 */
	public void debug(final String pattern, final Object... args) {
		
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(LogListeners.log(pattern, args));
		}
	}

	/**
	 * <p>log DEBUG level message with pattern, values of arguments and thrown exception. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog("name");
	 * ...
	 * try {
	 * 	...
	 * } catch (Exception e) {
	 * 	logger.debug("Something minor error happens at :{0}", new Date());
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param pattern the pattern that is used to formatted to logging message with arguments 
	 * @param e the thrown exception
	 * @param args the values of arguments for parameter pattern.
	 */
	public void debug(final String pattern, final Throwable e, final Object... args) {
		
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(LogListeners.log(pattern, e, args), e);
		}
	}

	/**
	 * @return whether debug level logging is enabled
	 */
	public boolean isInfoEnabled() {
		return this.logger.isInfoEnabled();
	}

	/**
	 * <p>log INFO level message with pattern and values of arguments. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog(Loader.class);
	 * ...
	 * logger.info("Load ORM entity object {0}.", TORDMST.class);
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param pattern the pattern that is used to formatted to logging message with arguments 
	 * @param args the values of arguments for parameter pattern.
	 */
	public void info(final String pattern, final Object... args) {
		
		if (this.logger.isInfoEnabled()) {
			this.logger.info(LogListeners.log(pattern, args));
		}
	}

	/**
	 * <p>log INFO level message with pattern, values of arguments and thrown exception. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog(Loader.class);
	 * ...
	 * try {
	 * 	...
	 * } catch (Exception e) {
	 * 	logger.info("Fail to load ORM entity class {0}.", TORDMST.class);
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param pattern the pattern that is used to formatted to logging message with arguments 
	 * @param e the thrown exception
	 * @param args the values of arguments for parameter pattern.
	 */
	public void info(final String pattern, final Throwable e, final Object... args) {
		
		if (this.logger.isInfoEnabled()) {
			this.logger.info(LogListeners.log(pattern, e, args), e);
		}
	}
	
	/**
	 * @return whether debug level logging is enabled
	 */
	public boolean isWarnEnabled() {
		return this.logger.isWarnEnabled();
	}

	/**
	 * <p>log WARN level message with pattern and values of arguments. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog(Loader.class);
	 * ...
	 * logger.warn("Can't find configuration file {0}, will use default.", "/logging.xml");
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param pattern the pattern that is used to formatted to logging message with arguments 
	 * @param args the values of arguments for parameter pattern.
	 */
	public void warn(final String pattern, final Object... args) {
		
		if (this.logger.isWarnEnabled()) {
			this.logger.warn(LogListeners.log(pattern, args));
		}
	}

	/**
	 * <p>log WARN level message with pattern, values of arguments and thrown exception. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog(Loader.class);
	 * ...
	 * try {
	 * 	...
	 * } catch (Exception e) {
	 * 	logger.warn("Fail to load configuration file {0}, will use default.", "/logging.xml");
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param pattern the pattern that is used to formatted to logging message with arguments 
	 * @param e the thrown exception
	 * @param args the values of arguments for parameter pattern.
	 */
	public void warn(final String pattern, final Throwable e, final Object... args) {
		
		if (this.logger.isWarnEnabled()) {
			this.logger.warn(LogListeners.log(pattern, e, args), e);
		}
	}

	/**
	 * @return whether debug level logging is enabled
	 */
	public boolean isErrorEnabled() {
		return this.logger.isErrorEnabled();
	}

	/**
	 * <p>log ERROR level message with pattern and values of arguments. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog(Loader.class);
	 * ...
	 * logger.error("Can't find configuration file {0}, will use default.", "/logging.xml");
	 * throw new ConfigurationNotFoundException("Fail to load configuration file", e, "/logging.xml");
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param pattern the pattern that is used to formatted to logging message with arguments 
	 * @param args the values of arguments for parameter pattern.
	 */
	public void error(final String pattern, final Object... args) {
		
		if (this.logger.isErrorEnabled()) {
			this.logger.error(LogListeners.log(pattern, args));
		}
	}

	/**
	 * <p>log ERROR level message with pattern, values of arguments and thrown exception. For example: 
	 * <pre>{@code
	 * private static final Log logger = LogFactory.getLog(Loader.class);
	 * ...
	 * try {
	 * 	...
	 * } catch (Exception e) {
	 * 	logger.error("Fail to load configuration file {0}, will use default.", "/logging.xml");
	 * 	throw new ConfigurationNotFoundException("Fail to load configuration file", e, "/logging.xml");
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @param pattern the pattern that is used to formatted to logging message with arguments 
	 * @param e the thrown exception
	 * @param args the values of arguments for parameter pattern.
	 */
	public void error(final String pattern, final Throwable e, final Object... args) {
		
		if (this.logger.isErrorEnabled()) {
			this.logger.error(LogListeners.log(pattern, e, args), e);
		}
	}
}
