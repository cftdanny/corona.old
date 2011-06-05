/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import org.dbunit.assertion.DbUnitAssert;

/**
 * <p>This assertion is used to test two tables or two table set are equal or not. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class Assertion {

	/**
	 * the data source tester
	 */
	private static final DbUnitAssert TESTOR = new DbUnitAssert();
	
	/**
	 * utility class
	 */
	private Assertion() {
		// do nothing
	}
	
	/**
	 * @param actual the actual table
	 * @param expected the expected table
	 * @throws Exception if two table is not same
	 */
	public static void assertEquals(final Table actual, final Table expected) throws Exception {
		TESTOR.assertEquals(expected.getSource(), actual.getSource());
	}

	/**
	 * @param actual the actual table
	 * @param expected the expected table
	 * @param ignoreColumns the columns that don't need to be compared
	 * @throws Exception if two table is not same
	 */
	public static void assertEquals(
			final Table actual, final Table expected, final String... ignoreColumns) throws Exception {
		TESTOR.assertEqualsIgnoreCols(expected.getSource(), actual.getSource(), ignoreColumns);
	}

	/**
	 * @param actual the actual table
	 * @param expected the expected table
	 * @throws Exception if two table is not same
	 */
	public static void assertEquals(final TableSet actual, final TableSet expected) throws Exception {
		TESTOR.assertEquals(expected.getSource(), actual.getSource());
	}
}
