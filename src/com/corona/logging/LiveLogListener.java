/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.util.Properties;

import com.corona.instant.LiveMessenger;
import com.corona.instant.Messenger;

/**
 * <p>Send log message to Windows Live Messenger </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class LiveLogListener extends MessengerLogListener {

	/**
	 * {@inheritDoc}
	 * @see com.corona.logging.MessengerLogListener#createMessenger(java.util.Properties)
	 */
	@Override
	protected Messenger createMessenger(final Properties properties) throws Exception {
		
		LiveMessenger messenger = new LiveMessenger();
		messenger.setUsername(this.getProperty(properties, "live", "username"));
		messenger.setPassword(this.getProperty(properties, "live", "password"));
		
		messenger.open();
		return messenger;
	}
}
