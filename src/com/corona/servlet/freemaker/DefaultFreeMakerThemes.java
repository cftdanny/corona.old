/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>The default FreeMaker themes </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DefaultFreeMakerThemes implements FreeMakerThemes {

	/**
	 * the default FreeMaker theme
	 */
	private FreeMakerTheme defaultTheme = new DefaultFreeMakerTheme();
	
	/**
	 * all FreeMaker themes
	 */
	private Map<String, FreeMakerTheme> themes = new HashMap<String, FreeMakerTheme>();
	
	/**
	 * @return the default FreeMaker theme
	 */
	public FreeMakerTheme getDefaultTheme() {
		return defaultTheme;
	}

	/**
	 * @param defaultTheme the default FreeMaker theme to set
	 */
	public void setDefaultTheme(final FreeMakerTheme defaultTheme) {
		this.defaultTheme = defaultTheme;
	}

	/**
	 * @return all theme names
	 */
	public String[] getThemeNames() {
		return this.themes.keySet().toArray(new String[0]);
	}
	
	/**
	 * @param themeName the theme name
	 * @param theme the theme
	 */
	public void add(final String themeName, final FreeMakerTheme theme) {
		this.themes.put(themeName, theme);
	}
	
	/**
	 * @param themeName the theme name to be removed
	 */
	public void remove(final String themeName) {
		this.themes.remove(themeName);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.freemaker.FreeMakerThemes#getThemeTemplate(java.lang.String, java.lang.String)
	 */
	@Override
	public String getThemeTemplate(final String themeName, final String template) {
		
		FreeMakerTheme foundTheme = this.themes.get(themeName);
		if (foundTheme != null) {
			return foundTheme.getThemeTemplate(template);
		} else {
			return this.defaultTheme.getThemeTemplate(template);
		}
	}
}
