/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.upload;

import java.io.InputStream;
import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.AbstractInjectParameter;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Upload;
import com.corona.servlet.multipart.MultipartRequest;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class UploadInjectParameter extends AbstractInjectParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(UploadInjectParameter.class);

	/**
	 * the component name
	 */
	private String name = null;

	/**
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	UploadInjectParameter(final Class<?> parameterType, final Annotation[] annotations) {
		
		super(parameterType, annotations);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(Upload.class)) {
				this.name = ((Upload) annotation).value();
				break;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectParameter#get(com.corona.context.ContextManager)
	 */
	@Override
	public Object get(final ContextManager contextManager) {
		
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
			} else if (this.getType().isArray() && this.getType().equals(byte.class)) {
				return multipartRequest.getFileBytes(this.name);
			} else {
				this.logger.error("Invalid parameter type [{0}], only support InputStream or byte[]", this.getType());
				throw new ValueException(
						"Invalid parameter type [{0}], only support InputStream or byte[]", this.getType()
				);
			}
		} else {
			this.logger.warn("Request is not a multipart request, just return null");
			return null;
		}
	}
}
