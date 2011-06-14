/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Line {

	/**
	 * to
	 */
	private String to;
	
	/**
	 * notes
	 */
	private List<String> notes = new ArrayList<String>();

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	
	/**
	 * @param to the to to set
	 */
	public void setTo(final String to) {
		this.to = to;
	}
	
	/**
	 * @return the notes
	 */
	public List<String> getNotes() {
		return notes;
	}
	
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(final List<String> notes) {
		this.notes = notes;
	}
}
