/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

/**
 * <p>The remote server that to handle request from remote client and produce response to remote client </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Server {

	byte[] setClientEncryptionKey();
	
	byte[] setClientDecryptionKey();
	
	byte[] setServerEncryptionKey();
	
	byte[] setServerDecryptionKey();
}
