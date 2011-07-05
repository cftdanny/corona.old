/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.component.mail;

import javax.mail.Session;

/**
 * <p>The default internet mail manager </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractMailManager {

	/**
	 * @return the java mail session
	 */
	protected abstract Session getSession();
}
