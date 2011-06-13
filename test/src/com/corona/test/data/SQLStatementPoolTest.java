/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.data;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.Query;
import com.corona.data.annotation.NamedCommand;
import com.corona.data.annotation.NamedCommands;
import com.corona.mock.AbstractBusinessTest;

/**
 * <p>This test is used to test SQL statement pool feature </p>
 *
 * @author $Author$
 * @version $Id$
 */
@NamedCommand("DELETE FROM TORDMST")
@NamedCommands(
		@NamedCommand(name = "test", value = "DELETE FROM TORDMST")
)
public class SQLStatementPoolTest extends AbstractBusinessTest {

	/**
	 * test pooled connection manager
	 * @exception Exception if fail
	 */
	@Test public void testPooledConnectionManager() throws Exception {

		ConnectionManager cm1 = this.getConnectionManager();
		cm1.close();
		
		ConnectionManager cm2 = this.getConnectionManagerFactory().open();
		Assert.assertEquals(cm2, cm1);
		cm2.close();
		
		ConnectionManager cm3 = this.getConnectionManagerFactory().open();
		Assert.assertEquals(cm3, cm1);

		ConnectionManager cm4 = this.getConnectionManagerFactory().open();
		Assert.assertNotSame(cm4, cm1);
		cm3.close();
		cm4.close();
	}
	
	/**
	 * test pooled connection manager, and reach to max idles connection pool
	 * @exception Exception if fail
	 */
	@Test public void testIdelPooledConnectionManager() throws Exception {

		// the default max idle connection manager pool size is 2
		ConnectionManager cm1 = this.getConnectionManager();
		ConnectionManager cm2 = this.getConnectionManagerFactory().open();
		ConnectionManager cm3 = this.getConnectionManagerFactory().open();

		// don't reach to max idles connection manager, will pool it
		cm1.close();
		cm2.close();
		
		// reach to max idles connection manager, will close it;
		cm3.close();
		
		// open more connection manager, but all should not be cm3, because it is closed
		ConnectionManager cm4 = this.getConnectionManagerFactory().open();
		Assert.assertNotSame(cm4, cm3);
		
		ConnectionManager cm5 = this.getConnectionManagerFactory().open();
		Assert.assertNotSame(cm5, cm3);

		ConnectionManager cm6 = this.getConnectionManagerFactory().open();
		Assert.assertNotSame(cm6, cm3);
		
		// close all connections
		cm4.close();
		cm5.close();
		cm6.close();
	}
	
	/**
	 * test pool primary key SQL statement 
	 * @exception Exception if error
	 */
	@Test public void testPoolPrimaryKeySQLStatement() throws Exception {
		
		// round 1, should create all statement
		HORDMST hordmst1 = new HORDMST(this.getConnectionManager());
		
		TORDMST order1 = new TORDMST();
		order1.setORDRNO("001");
		order1.setORDQTY(10);
		order1.setORDDAT(new java.sql.Date(new Date().getTime()));
		
		hordmst1.insert(order1);
		hordmst1.get(order1.getORDRID());
		
		order1.setORDQTY(20);
		hordmst1.update(order1);
		hordmst1.delete(order1.getORDRID());
		
		this.getConnectionManager().close();
		
		// round 2, should don't create statement, just use pooled statement
		HORDMST hordmst2 = new HORDMST(this.getConnectionManagerFactory().open());
		
		TORDMST order2 = new TORDMST();
		order2.setORDRNO("001");
		order2.setORDQTY(10);
		order2.setORDDAT(new java.sql.Date(new Date().getTime()));
		
		hordmst2.insert(order2);
		hordmst2.get(order2.getORDRID());
		
		order2.setORDQTY(20);
		hordmst2.update(order2);
		hordmst2.delete(order1.getORDRID());
	}
	
	/**
	 * test pool unique key SQL statement 
	 * @exception Exception if error
	 */
	@Test public void testPoolUniqueKeySQLStatement() throws Exception {
		
		// round 1, should create all statement
		HORDMST hordmst1 = new HORDMST(this.getConnectionManager());
		hordmst1.getUniqueKey(TORDMST.UK_ORDRNO).get(1L);
		hordmst1.getUniqueKey(TORDMST.UK_ORDRNO).delete(1L);
		this.getConnectionManager().close();
		
		// round 2, should don't create statement, just use pooled statement
		HORDMST hordmst2 = new HORDMST(this.getConnectionManagerFactory().open());
		hordmst2.getUniqueKey(TORDMST.UK_ORDRNO).get(1L);
		hordmst2.getUniqueKey(TORDMST.UK_ORDRNO).delete(1L);
		hordmst2.close();

		// round 2, should don't create statement, just use pooled statement
		HORDMST hordmst3 = new HORDMST(this.getConnectionManagerFactory().open());
		hordmst3.getUniqueKey(TORDMST.UK_ORDRNO).get(1L);
		hordmst3.getUniqueKey(TORDMST.UK_ORDRNO).delete(1L);
		hordmst3.close();
	}

	/**
	 * test pool index key SQL statement 
	 * @exception Exception if error
	 */
	@Test public void testPoolIndexSQLStatement() throws Exception {
		
		// round 1, should create all statement
		HORDMST hordmst1 = new HORDMST(this.getConnectionManager());
		hordmst1.getIndex(TORDMST.IX_ORDDAT).list(new Date());
		hordmst1.getIndex(TORDMST.IX_ORDDAT).delete(new Date());
		this.getConnectionManager().close();
		
		// round 2, should don't create statement, just use pooled statement
		HORDMST hordmst2 = new HORDMST(this.getConnectionManagerFactory().open());
		hordmst2.getIndex(TORDMST.IX_ORDDAT).list(new Date());
		hordmst2.getIndex(TORDMST.IX_ORDDAT).delete(new Date());
	}
	
	/**
	 * test pool index key SQL statement 
	 * @exception Exception if error
	 */
	@Test public void testPoolNamedCommand() throws Exception {
		
		// round 1, should create all command
		Command c1 = this.getConnectionManager().createNamedCommand(SQLStatementPoolTest.class);
		c1.delete();
		Command c2 = this.getConnectionManager().createNamedCommand(SQLStatementPoolTest.class, "test");
		c2.delete();
		this.getConnectionManager().close();
		
		// round 2, should don't create statement, just use pooled statement
		ConnectionManager cm = this.getConnectionManagerFactory().open();
		Command c3 = cm.createNamedCommand(SQLStatementPoolTest.class);
		c3.delete();
		Command c4 = cm.createNamedCommand(SQLStatementPoolTest.class, "test");
		c4.delete();
		
		// test, both command should be same
		Assert.assertEquals(c3, c1);
		Assert.assertEquals(c4, c2);
	}

	/**
	 * test pool index key SQL statement 
	 * @exception Exception if error
	 */
	@Test public void testPoolNamedQuery() throws Exception {
		
		// round 1, should create all query
		Query<SQLStatementPoolQuery> q1 = this.getConnectionManager().createNamedQuery(SQLStatementPoolQuery.class);
		q1.list();
		Query<SQLStatementPoolQuery> q2 = this.getConnectionManager().createNamedQuery(
				SQLStatementPoolQuery.class, "test"
		);
		q2.list();
		this.getConnectionManager().close();
		
		// round 2, should don't create statement, just use pooled statement
		ConnectionManager cm = this.getConnectionManagerFactory().open();
		Query<SQLStatementPoolQuery> q3 = cm.createNamedQuery(SQLStatementPoolQuery.class);
		q3.list();
		Query<SQLStatementPoolQuery> q4 = cm.createNamedQuery(
				SQLStatementPoolQuery.class, "test"
		);
		q4.list();
		
		// test, both command should be same
		Assert.assertEquals(q3, q1);
		Assert.assertEquals(q4, q2);
	}
}
