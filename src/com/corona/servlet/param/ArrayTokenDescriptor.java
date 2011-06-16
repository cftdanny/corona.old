/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

import java.util.List;

/**
 * <p>The descriptor for array token </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ArrayTokenDescriptor implements TokenDescriptor {

	/**
	 * the token name
	 */
	private String name;
	
	/**
	 * the array index
	 */
	private int index;
	
	/**
	 * @param name the token name
	 * @param index the index
	 */
	ArrayTokenDescriptor(final String name, final int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * @return name the token name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the array index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param runner the token runner
	 * @param parent parent object node
	 */
	private void addValueToObjectToken(final TokenRunner runner, final ObjectToken parent) {
		
		ArrayToken record = (ArrayToken) parent.get(this.name);
		if (record == null) {
			record = new ArrayToken(this.name);
			parent.set(this.name, record);
		}
		
		record.set(this.index, new ValueToken(runner.getValue()));
	}

	/**
	 * @param runner the token runner
	 * @param parent parent object node
	 */
	private void addValueToArrayToken(final TokenRunner runner, final ArrayToken parent) {
		
		ObjectToken object = (ObjectToken) parent.get(runner.getIndex());
		if (object == null) {
			object = new ObjectToken();
			parent.set(runner.getIndex(), object);
		}
	
		ArrayToken record = (ArrayToken) object.get(this.name);
		if (record == null) {
			record = new ArrayToken(this.name);
			object.set(this.name, record);
		}
		
		record.set(this.index, new ValueToken(runner.getValue()));
	}

	/**
	 * @param runner the token runner
	 * @param parent parent object node
	 * @return object node for this token
	 */
	private Token addArrayToObjectToken(final TokenRunner runner, final ObjectToken parent) {
		
		ArrayToken node = (ArrayToken) parent.get(this.name);
		if (node == null) {
			node = new ArrayToken(this.name);
			parent.set(this.name, node);
		}
		return node;
	}

	/**
	 * @param runner the token runner
	 * @param parent parent array node
	 * @return object node for this token
	 */
	private Token addArrayToArrayToken(final TokenRunner runner, final ArrayToken parent) {
		
		ObjectToken record = (ObjectToken) parent.get(runner.getIndex());
		if (record == null) {
			record = new ObjectToken();
			parent.set(runner.getIndex(), record);
		}
		
		ArrayToken node = (ArrayToken) record.get(this.name);
		if (node == null) {
			node = new ArrayToken(this.name);
			record.set(this.name, node);
		}
		
		return node;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.param.TokenDescriptor#create(
	 * 	com.corona.servlet.param.TokenRunner, java.util.List, com.corona.servlet.param.Token
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
				token = this.addArrayToObjectToken(runner, (ObjectToken) parent);
			} else {
				token = this.addArrayToArrayToken(runner, (ArrayToken) parent);
			}
		}
		runner.setIndex(this.index);

		return token;
	}
}
