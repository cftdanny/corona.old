/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.corona.context.annotation.Optional;
import com.corona.servlet.annotation.Chart;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.WebResource;

/**
 * <p>This class is used to create PNG with JFreeChart </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class IndexPng {

	/**
	 * the a
	 */
	@Param("a") @Optional private int a = 10;
	
	/**
	 * the b
	 */
	@Param("b") @Optional private int b = 20;
	
	/**
	 * collect data
	 */
	@Same("/index.png")
	@Chart(value = "produce", width = 600, height = 300) public void create() {
	}

	/**
	 * @return the chart
	 * @throws Exception if fail 
	 */
	public JFreeChart produce() throws Exception {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		dataset.addValue(this.a, "1", "A");
		dataset.addValue(this.b, "2", "B");
		
		return ChartFactory.createBarChart(
				"Chart", "Name", "Value", dataset, PlotOrientation.VERTICAL, true, true, false
		);
	}
}
