/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

import java.util.List;

/**
 * <p>The descriptor for object token </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ObjectTokenDescriptor implements TokenDescriptor {

	/**
	 * the token name
	 */
	private String name;
	
	/**
	 * @param name the token name
	 */
	ObjectTokenDescriptor(final String name) {
		this.name = name;
	}

	/**
	 * @return the token name
	 */
	String getName() {
		return this.name;
	}

	/**
	 * @param runner the token runner
	 * @param parent parent object token
	 */
	private void addValueToObjectToken(final TokenRunner runner, final ObjectToken parent) {
		parent.set(this.name, new ValueToken(this.name, runner.getValue()));
	}
	
	/**
	 * @param runner the token runner
	 * @param parent parent array node
	 */
	private void addValueToArrayToken(final TokenRunner runner, final ArrayToken parent) {
		
		ObjectToken object = (ObjectToken) parent.get(runner.getIndex());
		if (object == null) {
			object = new ObjectToken();
			parent.set(runner.getIndex(), object);
		}
		object.set(this.name, new ValueToken(this.name, runner.getValue()));
	}
	
	/**
	 * @param runner the token runner
	 * @param parent parent object token
	 * @return object token for this descriptor
	 */
	private ObjectToken addObjectToObjectToken(final TokenRunner runner, final ObjectToken parent) {
		
		ObjectToken node = (ObjectToken) parent.get(this.name);
		if (node == null) {
			node = new ObjectToken(this.name);
			parent.set(this.name, node);
		}
		return node;
	}

	/**
	 * @param runner the token runner
	 * @param parent parent array token
	 * @return array token for this descriptor
	 */
	private ObjectToken addObjectToArrayToken(final TokenRunner runner, final ArrayToken parent) {
		
		ObjectToken record = (ObjectToken) parent.get(runner.getIndex());
		if (record == null) {
			record = new ObjectToken();
			parent.set(runner.getIndex(), record);
		}
		
		ObjectToken node = (ObjectToken) record.get(this.name);
		if (node == null) {
			node = new ObjectToken(this.name);
			record.set(this.name, node);
		}
		
		return node;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.injecting.param.TokenDescriptor#create(
	 * 	com.corona.servlet.injecting.param.TokenRunner, java.util.List, com.corona.servlet.injecting.param.Token
	 * )
	 */
	@Override
	public Token create(
			final TokenRunner runner, final List<TokenDescriptor> descriptors, final Token parent
	) throws TokenParserException {
		
		Token token = null;
		if (descriptors.isEmpty()) {
			
			if (parent instanceof ObjectToken) {
				this.addValueToObjectToken(runner, (ObjectToken) parent);
			} else {
				this.addValueToArrayToken(runner, (ArrayToken) parent);
			}
		} else {
			
			if (parent instanceof ObjectToken) {
				token = this.addObjectToObjectToken(runner, (ObjectToken) parent);
			} else {
				token = this.addObjectToArrayToken(runner, (ArrayToken) parent);
			}
		}
		runner.setIndex(-1);
		
		return token;
	}
}
