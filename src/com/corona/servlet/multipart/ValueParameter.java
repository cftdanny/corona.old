/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.multipart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>This parameter is used to store sample  </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ValueParameter extends Parameter {

	/**
	 * how to encode data
	 */
	private String encoding = null;

	/**
	 * the value
	 */
	private Object value = null;

	/**
	 * the value as output stream
	 */
	private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	/**
	 * @param name the parameter name
	 * @param encoding how to encode data
	 */
	ValueParameter(final String name, final String encoding) {
		super(name);
		this.encoding = encoding;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Parameter#appendData(byte[], int, int)
	 */
	@Override
	public void appendData(final byte[] data, final int start, final int length) throws IOException {
		buffer.write(data, start, length);
	}

	/**
	 * @throws UnsupportedEncodingException if fail to encode data
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void complete() throws UnsupportedEncodingException {
		
		String data;
		if (this.encoding == null) {
			data = new String(buffer.toByteArray());
		} else {
			data = new String(buffer.toByteArray(), this.encoding);
		}
		buffer.reset();
		
		if (value != null) {
			
			if (!(value instanceof List)) {
				List<String> list = new ArrayList<String>();
				list.add((String) value);
				value = list;
			}
			((List) value).add(data);
		} else {
			value = data;
		}
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
}
