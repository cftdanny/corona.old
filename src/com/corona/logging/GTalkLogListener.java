/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.logging;

import java.util.Properties;

import com.corona.instant.GTalkMessenger;
import com.corona.instant.Messenger;

/**
 * <p>Send log message to Google Talk </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class GTalkLogListener extends MessengerLogListener {

	/**
	 * {@inheritDoc}
	 * @see com.corona.logging.MessengerLogListener#createMessenger(java.util.Properties)
	 */
	@Override
	protected Messenger createMessenger(final Properties properties) throws Exception {
		
		GTalkMessenger messenger = new GTalkMessenger();
		messenger.setDomain(this.getProperty(properties, "gtalk", "domain"));
		messenger.setUsername(this.getProperty(properties, "gtalk", "username"));
		messenger.setPassword(this.getProperty(properties, "gtalk", "password"));
		
		messenger.open();
		return messenger;
	}
}
