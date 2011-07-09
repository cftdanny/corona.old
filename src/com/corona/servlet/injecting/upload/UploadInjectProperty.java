/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.upload;

import java.beans.Introspector;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectProperty;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Upload;
import com.corona.servlet.multipart.MultipartRequest;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class UploadInjectProperty extends AbstractInjectProperty {
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(UploadInjectProperty.class);

	/**
	 * the name
	 */
	private String name = null;
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	UploadInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		
		super(contextManagerFactory, property);
		
		this.name = this.getMethod().getAnnotation(Upload.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = Introspector.decapitalize(this.getMethod().getName().substring(3));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectProperty#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {

		HttpServletRequest request = contextManager.get(HttpServletRequest.class);
		if (request == null) {
			this.logger.error("Not running at Web Container, can not find Servlet Request");
			throw new ValueException("Not running at Web Container, can not find Servlet Request");
		}

		// test whether request is multipart request, if not, reject
		if (request instanceof MultipartRequest) {
			
			MultipartRequest multipartRequest = (MultipartRequest) request;
			if (InputStream.class.isAssignableFrom(this.getType())) {
				return multipartRequest.getFileInputStream(this.name);
			} else if (this.getType().isArray() && this.getType().equals(byte[].class)) {
				return multipartRequest.getFileBytes(this.name);
			} else {
				this.logger.error("Invalid property type [{0}], only support InputStream or byte[]", this.getMethod());
				throw new ValueException(
						"Invalid property type [{0}], only support InputStream or byte[]", this.getMethod()
				);
			}
		} else {
			this.logger.warn("Request is not a multipart request, just return null");
			return null;
		}
	}
}
