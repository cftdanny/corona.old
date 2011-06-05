/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import org.testng.annotations.Test;

import com.corona.mock.AbstractBusinessTest;
import com.corona.mock.Assertion;
import com.corona.mock.TableSet;

/**
 * <p>This test is used to test data predicate </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DatabaseAssertionTest extends AbstractBusinessTest {

	/**
	 * @throws Exception if fail
	 */
	@Test void testPredicate() throws Exception {
		
		TableSet source = this.loadDatabase();
		
		this.begin();
		TableSet target = this.queryTableSet("TORDMST");
		this.rollback();
		
		Assertion.assertEquals(target, source);
	}
}
