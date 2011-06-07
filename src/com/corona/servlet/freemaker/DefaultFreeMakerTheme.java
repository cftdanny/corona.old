/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import com.corona.util.StringUtil;

/**
 * <p>The default theme that matches theme template for a to be processed template by heading path. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DefaultFreeMakerTheme implements FreeMakerTheme {

	/**
	 * the heading
	 */
	private String heading;
	
	/**
	 * the template
	 */
	private String themeTemplate;
	
	/**
	 * default constructor, only used in DefaultFreeMakerThemes
	 */
	DefaultFreeMakerTheme() {
		this("", null);
	}
	
	/**
	 * @param heading the template heading
	 * @param themeTemplate the theme template
	 */
	public DefaultFreeMakerTheme(final String heading, final String themeTemplate) {
		this.heading = StringUtil.isBlank(heading) ? "" : heading.trim();
		this.themeTemplate = themeTemplate;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.freemaker.FreeMakerTheme#getThemeTemplate(java.lang.String)
	 */
	@Override
	public String getThemeTemplate(final String template) {
		return template.startsWith(this.heading) ? this.themeTemplate : null;
	}
}
