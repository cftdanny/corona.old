/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * <p>This formatter is used to format logging message for IM client </p>
 *
 * @author $Author$
 * @version $Id$
 */
class DisplayFormatter extends Formatter {

	/**
	 * the formatter
	 */
	private static final DateFormat PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * {@inheritDoc}
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(final LogRecord record) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(PATTERN.format(new Date(record.getMillis()))).append(" ");
		builder.append(record.getLevel()).append(" ");
		builder.append(record.getLoggerName()).append(" - ");
		builder.append(record.getMessage());

		return builder.toString();
	}
}
