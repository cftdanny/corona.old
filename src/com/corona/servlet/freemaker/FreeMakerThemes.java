/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

/**
 * <p>The FreeMaker themes that is used to find theme for a to be processed template. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface FreeMakerThemes {

	/**
	 * @param themeName the theme name
	 * @param template the template to be processed
	 * @return the theme template or <code>null</code> if does not exist
	 */
	String getThemeTemplate(String themeName, String template);
}
