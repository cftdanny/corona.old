/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Template;

/**
 * <p>The FreeMaker template engine. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface FreeMakerEngineManager {

	/**
	 * @return all defined theme templates
	 */
	Map<String, String> getThemeTemplates();

	/**
	 * @return the default theme name
	 */
	String getDefaultThemeName();

	/**
	 * @return the variable name is used to include child template
	 */
	String getThemeTemplateVariableName();

	/**
	 * @return the applied theme name to get from request or session by attribute name 
	 */
	String getThemeRequestAttributeName();

	/**
	 * <p>Compile source FreeMaker template into compiled template. </p>
	 * 
	 * @param name the template
	 * @param locale the language for template
	 * @return the compiled template
	 * @throws IOException if template can not be found
	 */
	Template compile(final String name, final Locale locale) throws IOException;
}
