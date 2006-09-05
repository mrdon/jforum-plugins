/*
 * Created on 04/09/2006 22:04:17
 */
package net.jforum.dao;

/**
 * @author Rafael Steil
 * @version $Id: ApiDAO.java,v 1.1 2006/09/05 01:20:09 rafaelsteil Exp $
 */
public interface ApiDAO
{
	/**
	 * Check if the given API authentication information is valid.
	 * @param apiKey the api key
	 * @param apiHash the api hash
	 * @return <code>true</code> if the information is correct
	 */
	public boolean isValid(String apiKey, String apiHash);
}
