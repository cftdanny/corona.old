/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.corona.context.ContextManager;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * <p>This FreeMaker template model is used to directly export properties, method to template. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class FreeMakerDataModel extends BeanModel {

	/**
	 * the context manager
	 */
	private ContextManager contextManager;
	
	/**
	 * the FreeMaker engine
	 */
	private FreeMakerEngineManager freeMakerEngineManager;
	
	/**
	 * the child template
	 */
	private String childTemplate;
	
	/**
	 * the current HTTP request
	 */
	private HttpServletRequest request;
	
	/**
	 * the current HTTP session
	 */
	private HttpSession session;
	
	/**
	 * the current HTTP response
	 */
	private HttpServletResponse response;
	
	/**
	 * @param contextManager the current context manager
	 * @param root the root outcome from FreeMaker producer
	 */
	public FreeMakerDataModel(final ContextManager contextManager, final Object root) {
		super(root, new BeansWrapper());
		this.contextManager = contextManager;
	}
	
	/**
	 * @param freeMakerEngineManager the freeMakerEngineManager to set
	 */
	public void setFreeMakerEngineManager(final FreeMakerEngineManager freeMakerEngineManager) {
		this.freeMakerEngineManager = freeMakerEngineManager;
	}

	/**
	 * @param childTemplate the child template to set
	 */
	public void setChildTemplate(final String childTemplate) {
		this.childTemplate = childTemplate;
	}

	/**
	 * {@inheritDoc}
	 * @see freemarker.ext.beans.BeanModel#get(java.lang.String)
	 */
	@Override
	public TemplateModel get(final String name) throws TemplateModelException {
		
		TemplateModel model = super.get(name);
		if (model != null) {
			return model;
		} else if ("request".equals(name)) {
			return this.wrap(this.getRequest());
		} else if ("response".equals(name)) {
			return this.wrap(this.getResponse());
		} else if ("session".equals(name)) {
			return this.wrap(this.getSession());
		} else if (this.freeMakerEngineManager.getThemeTemplateVariableName().equals(name)) {
			return this.wrap(this.childTemplate);
		} else {
			Object component = this.contextManager.get(name);
			return (component != null) ? this.wrap(component) : null;
		}
	}
	
	/**
	 * @return the current HTTP request
	 */
	HttpServletRequest getRequest() {
		
		if (this.request == null) {
			this.request = this.contextManager.get(HttpServletRequest.class);
		}
		return this.request;
	}
	
	/**
	 * @return the current HTTP session 
	 */
	HttpSession getSession() {
		
		if (this.session == null) {
			this.session = this.contextManager.get(HttpSession.class);
		}
		return this.session;
	}
	
	/**
	 * @return the current HTTP response
	 */
	HttpServletResponse getResponse() {
		
		if (this.response == null) {
			this.response = this.contextManager.get(HttpServletResponse.class);
		}
		return response;
	}
	
	/**
	 * @return get theme name from request or session with this attribute name
	 */
	private String getThemeAttributeName() {
		return this.freeMakerEngineManager.getThemeRequestAttributeName();
	}
	
	/**
	 * @param themeName the theme name
	 * @return the theme template
	 */
	private String getThemeTemplate(final String themeName) {
		return this.freeMakerEngineManager.getThemes().getThemeTemplate(themeName, this.childTemplate);
	}
	
	/**
	 * @return the theme name that is defined by cookie
	 */
	private String getCookieThemeName() {
		
		Cookie[] cookies = this.getRequest().getCookies();
		if (cookies != null) {
			String contextPath = this.getSession().getServletContext().getContextPath();
			for (Cookie cookie : cookies) {
				if (contextPath.equals(cookie.getPath())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * @param themeName the theme name
	 */
	private void setCookieThemeName(final String themeName) {
		
		Cookie cookie = new Cookie(this.getThemeAttributeName(), themeName);
		cookie.setPath(this.getSession().getServletContext().getContextPath());
		this.getResponse().addCookie(cookie);
	}
	
	/**
	 * @return the theme template
	 */
	String getThemeName() {
		
		// find theme name from request parameter first. If can't find, try other way
		String themeName = this.getRequest().getParameter(this.getThemeAttributeName());
		if (themeName == null) {
			// if theme name is defined in cookie, will store it to cookie   
			themeName = this.getCookieThemeName();
		} else {
			// store request theme name to cookie for later usage 
			this.setCookieThemeName(themeName);
		}
		
		// find theme template by theme name from FreeMaker engine
		if (themeName != null) {
			
			String themeTemplate = this.getThemeTemplate(themeName);
			if (themeTemplate != null) {
				return themeTemplate;
			}
		}
		
		// find theme template by default theme name
		themeName = this.freeMakerEngineManager.getDefaultThemeName();
		if (themeName != null) {
			// store default theme name to cookie for later usage
			this.setCookieThemeName(themeName);
		}
		
		return this.getThemeTemplate(themeName);
	}
}
