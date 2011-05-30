/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.chart;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.corona.context.ContextManager;
import com.corona.context.InjectMethod;
import com.corona.context.Key;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;
import com.corona.servlet.annotation.Chart;

/**
 * <p>This producer is used to create HTTP response by FreeMaker and annotated method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ChartProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ChartProducer.class);
	
	/**
	 * the chart configuration in producer method
	 */
	private Chart chart;
	
	/**
	 * @param key the component key
	 * @param method the method that is annotated with {@link FreeMaker}
	 */
	public ChartProducer(final Key<?> key, final InjectMethod method) {
		super(key, method);
		this.chart = method.getMethod().getAnnotation(Chart.class);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Producer#produce(
	 * 	com.corona.context.ContextManager, javax.servlet.http.HttpServletResponse, java.io.OutputStream, 
	 * 	java.lang.Object, java.lang.Object
	 * )
	 */
	@Override
	public void produce(
			final ContextManager contextManager, final HttpServletResponse response, final OutputStream out,
			final Object component, final Object data) throws ProduceException {
		
		// find component and the method that is used to create PDF document
		Method method = null;
		try {
			if (data != null) {
				method = component.getClass().getMethod(this.chart.value(), data.getClass());
			} else {
				method = component.getClass().getMethod(this.chart.value());
			}
		} catch (Throwable e) {
			
			this.logger.error("Method [{0}] to create chart in component [{1}] does not exist", 
					e, this.chart.value(), this.getKey()
			);
			throw new ProduceException("Method [{0}] to create chart in component [{1}] does not exist", 
					e, this.chart.value(), this.getKey()
			);
		}
		
		// execute method to create chart
		JFreeChart source = null;
		try {
			if (data != null) {
				source = (JFreeChart) method.invoke(component, data);
			} else {
				source = (JFreeChart) method.invoke(component);
			}
		} catch (Throwable e) {
			
			this.logger.error("Fail when invoke method [{0}] of component [{1}] to create chart", 
					e, this.chart.value(), this.getKey()
			);
			throw new ProduceException("Fail when invoke method [{0}] of component [{1}] to create chart", 
					e, this.chart.value(), this.getKey()
			);
		}
		
		// set content type if it is not set yet
		if (response.getContentType() == null) {
			response.setContentType(this.chart.png() ? "image/png" : "image/jpeg");
		}
		
		// create picture by chart and stream to response
		try {
			if (this.chart.png()) {
				ChartUtilities.writeChartAsPNG(
						out, source, this.chart.width(), this.chart.height(), 
						this.chart.alpha(), this.chart.compression()
				);
			} else {
				ChartUtilities.writeChartAsJPEG(
						out, this.chart.quality(), source, this.chart.width(), this.chart.height()
				);
			}
		} catch (IOException e) {
			
			this.logger.error("Fail when create response stream by method [{0}] of component [{1}]", 
					e, this.chart.value(), this.getKey()
			);
			throw new ProduceException("Fail when create response stream by method [{0}] of component [{1}]", 
					e, this.chart.value(), this.getKey()
			);
		}
	}
}
