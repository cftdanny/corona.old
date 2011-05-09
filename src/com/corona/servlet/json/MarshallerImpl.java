/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.json;

import java.io.OutputStream;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.corona.context.annotation.Create;
import com.corona.context.annotation.Dependency;
import com.corona.servlet.ProduceException;

/**
 * <p>This marshaller is used to generate JSON content with object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Dependency("org.codehaus.jackson.map.ObjectMapper")
public class MarshallerImpl implements Marshaller {

	/**
	 * the JSON generator
	 */
	private ObjectMapper generator = new ObjectMapper();
	
	/**
	 * initial JSON generator with JABX annotation support
	 */
	@Create public void init() {
		
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		
		this.generator.setAnnotationIntrospector(introspector);
		this.generator.setAnnotationIntrospector(introspector);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.json.Marshaller#marshal(java.io.OutputStream, java.lang.Object)
	 */
	public void marshal(final OutputStream out, final Object root) throws ProduceException {
		
		try {
			this.generator.writeValue(out, root);
		} catch (Throwable e) {
			throw new ProduceException("Fail to marshal object [{0}] to JSON", e, root.getClass());
		}
	}
}
