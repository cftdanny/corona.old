/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import java.util.Properties;

import com.corona.context.AbstractModule;
import com.corona.data.DataSourceManagerProvider;
import com.corona.data.DataSourceProvider;
import com.corona.data.DataSourceSupportModule;

/**
 * <p>This module is used in testing environment only. In this module, it will define components for
 * testing purpose only. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CoronaTestingModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		// configure default testing data source 
		this.bindModule(new DataSourceSupportModule());
		
		Properties settings = new Properties();
		settings.setProperty(DataSourceProvider.URL, "jdbc:hsqldb:res:/test");
		settings.setProperty(DataSourceProvider.USER, "sa");
		settings.setProperty(DataSourceProvider.PASSWORD, "");
		
		this.bindConfiguration(DataSourceManagerProvider.class).property("family").value("HSQL");
		this.bindConfiguration(DataSourceManagerProvider.class).property("settings").value(settings);
	}
}
