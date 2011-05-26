/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import java.util.Properties;

import com.corona.context.AbstractModule;
import com.corona.data.DataSourceProvider;
import com.corona.support.DataSourceManagerProvider;
import com.corona.support.DataSourceModule;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DatabaseModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		this.bindModule(new DataSourceModule());
		
		Properties settings = new Properties();
		settings.setProperty(DataSourceProvider.URL, "jdbc:hsqldb:res:/test");
		settings.setProperty(DataSourceProvider.USER, "sa");
		settings.setProperty(DataSourceProvider.PASSWORD, "");
		
		this.bindSetting(DataSourceManagerProvider.class).to("family").with("HSQL");
		this.bindSetting(DataSourceManagerProvider.class).to("settings").with(settings);
	}
}
