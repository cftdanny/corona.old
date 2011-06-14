/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.DeserializationProblemHandler;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

/**
 * <p>The token runner is used to translate parameter name and expression to object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TokenRunner extends DeserializationProblemHandler {

	/**
	 * the object mapper
	 */
	private ObjectMapper mapper;
	
	/**
	 * the HTTP SERVLET request
	 */
	private HttpServletRequest request;
	
	/**
	 * @param request the HTTP SERVLET request
	 */
	TokenRunner(final HttpServletRequest request) {
		this.request = request;
		
		this.mapper = new ObjectMapper();
		this.mapper.getDeserializationConfig().addHandler(this);
	}
	
	/**
	 * @return the object mapper
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
	
	/**
	 * @return the HTTP SERVLET request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * {@inheritDoc}
	 * @see org.codehaus.jackson.map.DeserializationProblemHandler#handleUnknownProperty(
	 * 	org.codehaus.jackson.map.DeserializationContext, org.codehaus.jackson.map.JsonDeserializer, 
	 * 	java.lang.Object, java.lang.String
	 * )
	 */
	@Override
	public boolean handleUnknownProperty(
			final DeserializationContext ctxt, final JsonDeserializer<?> deserializer, final Object beanOrClass, 
			final String propertyName) 
	throws IOException {
		return true;
	}

	/**
	 * @param type the class type
	 * @return the translated value
	 * @exception TokenParserException if fail to translate request parameters to value
	 */
	@SuppressWarnings("unchecked")
	Object getValue(final Class<?> type) throws TokenParserException {
		
		// find all request parameter names
		Enumeration<String> names = (Enumeration<String>) this.request.getParameterNames();
		
		// parse every parameter name and create Jackson JSON node
		ObjectNode root = this.mapper.createObjectNode();
		while (names.hasMoreElements()) {
			
			String name = names.nextElement();
			List<Token> tokens = TokenParser.parse(name);
			
			ObjectNode current = root;
			while (!tokens.isEmpty()) {
				current = tokens.remove(0).create(this, name, tokens, current);
			}
		}
		
		// translate JSON node to object
		try {
			return mapper.readValue(root, type);
		} catch (Exception e) {
			throw new TokenParserException(
					"Fail to translate request parameter to class [" + type.getName() + "]", e
			);
		}
	}
}
