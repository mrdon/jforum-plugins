/*
 * Created on 04/09/2006 21:59:39
 */
package net.jforum.api.rest;

import net.jforum.dao.ApiDAO;
import net.jforum.dao.DataAccessDriver;

/**
 * @author Rafael Steil
 * @version $Id: RESTAuthentication.java,v 1.1 2006/09/05 01:20:09 rafaelsteil Exp $
 */
public class RESTAuthentication
{
	public boolean validateApiKey(String apiKey, String apiHash)
	{
		ApiDAO dao = DataAccessDriver.getInstance().newApiDAO();
		return dao.isValid(apiKey, apiHash);
	}
}
