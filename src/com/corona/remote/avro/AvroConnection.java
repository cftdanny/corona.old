/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import com.corona.remote.AbstractConnection;
import com.corona.remote.ClientConfiguration;
import com.corona.remote.RemoteException;

/**
 * <p>The connection for Apache Avro </p>
 *
 * @author $Author$
 * @version $Id$
 */
class AvroConnection extends AbstractConnection {

	/**
	 * @param configurator the server configurator
	 * @param serviceName the service name
	 * @throws RemoteException if fail to create connection
	 */
	AvroConnection(final ClientConfiguration configurator, final String serviceName) throws RemoteException {
		super(configurator, serviceName);
	}
}
