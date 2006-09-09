/*
 * Created on 09/09/2006 17:00:27
 */
package net.jforum.api.integration.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import net.jforum.JForumExecutionContext;
import net.jforum.TestCaseUtils;
import net.jforum.api.rest.RESTAuthentication;

/**
 * @author Rafael Steil
 * @version $Id: RESTAuthenticationTestCase.java,v 1.2 2006/09/09 21:32:36 rafaelsteil Exp $
 */
public class RESTAuthenticationTestCase extends TestCase
{
	public static final String API_KEY = "api.key.test";
	public static final String API_HASH = "api.hash.test";
	
	private Connection connection;
	
	public void testInvalid() throws Exception
	{
		RESTAuthentication auth = new RESTAuthentication();
		boolean isValid = auth.validateApiKey("1", "2");
		
		assertFalse("The api key should not be valid", isValid);
	}
	
	public void testValid() throws Exception
	{
		RESTAuthentication auth = new RESTAuthentication();
		boolean isValid = auth.validateApiKey(API_KEY, API_HASH);
		
		assertTrue("The api key should be valid", isValid);
	}
	
	Date tomorrow()
	{
		Calendar c = Calendar.getInstance();
		return new GregorianCalendar(c.get(Calendar.YEAR), 
			c.get(Calendar.MONTH), 
			c.get(Calendar.DATE) + 1).getTime();
	}

	/**
	 * @throws SQLException
	 */
	void createApiKey(Date validity) throws SQLException
	{
		PreparedStatement p = null;
		
		try {
			p = JForumExecutionContext.getConnection()
				.prepareStatement("INSERT INTO jforum_api (api_key, api_hash, api_validity) "
						+ " VALUES (?, ?, ?)");
			p.setString(1, API_KEY);
			p.setString(2, API_HASH);
			p.setTimestamp(3, new Timestamp(validity.getTime()));
			p.executeUpdate();
		}
		finally {
			if (p != null) p.close();
		}
	}

	/**
	 * @throws SQLException
	 */
	void deleteApiKey() throws SQLException
	{
		PreparedStatement p = null;
		
		try {
			p = JForumExecutionContext.getConnection()
				.prepareStatement("DELETE FROM jforum_api WHERE api_key = ? AND api_hash = ?");
			p.setString(1, API_KEY);
			p.setString(2, API_HASH);
			p.executeUpdate();
		}
		finally {
			if (p != null) p.close();
		}
	}
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		TestCaseUtils.loadEnvironment();
		TestCaseUtils.initDatabaseImplementation();
		this.createApiKey(this.tomorrow());
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		this.deleteApiKey();
		JForumExecutionContext.finish();
	}
}
