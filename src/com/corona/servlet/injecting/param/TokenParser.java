/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import java.util.ArrayList;
import java.util.List;

import com.corona.util.StringUtil;

/**
 * <p>This parser is used to parse parameter name or expression to tokens. </p>
 *
 * @author $Author$
 * @version $Id$
 */
final class TokenParser {

	/**
	 * utility class
	 */
	private TokenParser() {
		// do nothing
	}
	
	/**
	 * @param expression parameter name or expression
	 * @return the parsed tokens
	 * @exception TokenParserException if fail to parse expression
	 */
	public static List<TokenDescriptor> parse(final String expression) throws TokenParserException {
		
		// if parameter name or expression is blank, throw error
		if (StringUtil.isBlank(expression)) {
			throw new TokenParserException("Parameter name or expression can not be blank.");
		}
		
		// parse parameter name or expression
		int state = 0;
		
		// the parsing field name and array index
		StringBuilder name = new StringBuilder(), index = null;
		
		// start to parse expression
		List<TokenDescriptor> tokens = new ArrayList<TokenDescriptor>();
		for (char c : expression.trim().toCharArray()) {
			
			switch (state) {
				
				case 0:
					if (c == '.') {
						if (name.length() == 0) {
							throw new TokenParserException("Invalid expression at ., maybe double it");
						}
						tokens.add(new ObjectTokenDescriptor(name.toString()));
						name = new StringBuilder();
					} else if (c == '[') {
						index = new StringBuilder();
						state = 1;
					} else {
						name.append(c);
					}
					break;
					
				case 1:
					if (c == ']') {
						if ((name.length() == 0) || (index.length() == 0)) {
							throw new TokenParserException("For array, name or index can not be empty");
						}
						try {
							tokens.add(new ArrayTokenDescriptor(name.toString(), Integer.parseInt(index.toString())));
						} catch (Exception e) {
							throw new TokenParserException("The index of array must be INTEGER");
						}
						name = new StringBuilder();
						index = new StringBuilder();
						
						state = 2;
					} else {
						index.append(c);
					}
					break;
					
				case 2:
					if (c == '.') {
						state = 0;
					} else if (c != ' ') {
						throw null;
					}
					break;
					
				default:
					throw new TokenParserException("Invalid status, but it should not happens");
			}
		}
		
		// check parse state and add token if not added yet
		if ((state == 0) && (name.length() > 0)) {
			tokens.add(new ObjectTokenDescriptor(name.toString()));
		} else if (state == 1) {
			throw new TokenParserException("Invalid status, but it should not happens");
		}
		
		return tokens;
	}
}
