/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletRequest;

/**
 * <p>The messages is used to display I18N message on browser. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class Messages {

	/**
	 * the i18n resource bundle
	 */
	private ResourceBundle resource;
	
	/**
	 * {@link ApplicationServlet#service(ServletRequest, javax.servlet.ServletResponse)}
	 */
	public static final String HANDLER_PRODUCE_ERROR = "handler.produce.error";

	/**
	 * {@link Handlers#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 */
	public static final String PAGE_NOT_FOUND = "page.not.found";
	
	/**
	 * @param locale the locale for browser
	 */
	private Messages(final Locale locale) {
		this.resource = ResourceBundle.getBundle("/" + Messages.class.getName().replace('.', '/'), locale);
	}

	/**
	 * @param request the SERVLET request
	 */
	public Messages(final ServletRequest request) {
		this(request.getLocale());
	}
	
	/**
	 * @param key the key
	 * @param args the arguments to be substituted in message
	 * @return the value
	 */
	public String get(final String key, final Object... args) {
		
		String message = this.resource.getString(key);
		if ((message != null) && (args != null) && (args.length > 0)) {
			message = MessageFormat.format(message, args);
		}
		return message;
	}
}
