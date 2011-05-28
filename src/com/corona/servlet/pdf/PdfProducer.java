/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.pdf;

import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.AbstractProducer;
import com.corona.servlet.ProduceException;
import com.corona.servlet.annotation.Pdf;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * <p>This producer is used to create HTTP response by FreeMaker and annotated method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class PdfProducer extends AbstractProducer {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(PdfProducer.class);
	
	/**
	 * the method to create PDF document with producer result
	 */
	private String methodName;
	
	/**
	 * @param key the component key
	 * @param method the method that is annotated with {@link FreeMaker}
	 */
	public PdfProducer(final Key<?> key, final DecoratedMethod method) {
		super(key, method);
		this.methodName = method.getMethod().getAnnotation(Pdf.class).value();
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
		
		// set content type if it is not set yet
		if (response.getContentType() == null) {
			response.setContentType("application/pdf");
		}

		// create PDF document and initiate its PDF writer
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {
			
			this.logger.error("Fail to create PDF writer for component key [{0}]", this.getKey());
			throw new ProduceException("Fail to create PDF writer for component key [{0}]", this.getKey());
		}
		document.open();
		
		// find component and the method that is used to create PDF document
		Method method = null;
		try {
			if (data != null) {
				method = component.getClass().getMethod(this.methodName, Document.class, data.getClass());
			} else {
				method = component.getClass().getMethod(this.methodName, Document.class);
			}
		} catch (Throwable e) {
			
			this.logger.error("Method [{0}] to create PDF in component [{1}] does not exist", 
					e, this.methodName, this.getKey()
			);
			throw new ProduceException("Method [{0}] to create PDF in component [{1}] does not exist", 
					e, this.methodName, this.getKey()
			);
		}
		
		// execute method to create PDF document and send to browser by HTTP
		try {
			if (data != null) {
				method.invoke(component, document, data);
			} else {
				method.invoke(component, document);
			}
		} catch (Throwable e) {
			
			this.logger.error("Fail when invoke method [{0}] of component [{1}] to create PDF", 
					e, this.methodName, this.getKey()
			);
			throw new ProduceException("Fail when invoke method [{0}] of component [{1}] to create PDF", 
					e, this.methodName, this.getKey()
			);
		}
		
		document.close();
	}
}
