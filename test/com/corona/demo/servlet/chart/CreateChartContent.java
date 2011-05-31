/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.demo.servlet.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.corona.servlet.annotation.Chart;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.WebResource;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class CreateChartContent {

	/**
	 * the a
	 */
	private int a;
	
	/**
	 * the b
	 */
	private int b;
	
	/**
	 * @return the user
	 */
	@Same("/test.png")
	@Chart(value = "fill", width = 600, height = 300) public CreateChartContent findData() {
		
		this.a = 10;
		this.b = 20;
		return this;
	}

	/**
	 * @param data data
	 * @return jfree chart
	 * @throws Exception if fail 
	 */
	public JFreeChart fill(final CreateChartContent data) throws Exception {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		dataset.addValue(this.a, "1", "A");
		dataset.addValue(this.b, "2", "B");
		
		return ChartFactory.createBarChart(
				"TEST", "name", "value", dataset, PlotOrientation.VERTICAL, true, true, false
		);
	}
}
