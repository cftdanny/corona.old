/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.freemaker;

/**
 * <p>This theme is used to find theme template for a to be processed template. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface FreeMakerTheme {

	/**
	 * @param template the template to be processed in FreeMaker engine
	 * @return the theme template or <code>null</code> if does not exist
	 */
	String getThemeTemplate(String template);
}
