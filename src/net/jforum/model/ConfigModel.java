/*
 * Copyright (c) 2003, Rafael Steil
 * All rights reserved.

 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:

 * 1) Redistributions of source code must retain the above 
 * copyright notice, this list of conditions and the 
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the 
 * above copyright notice, this list of conditions and 
 * the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor 
 * the names of its contributors may be used to endorse 
 * or promote products derived from this software without 
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT 
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, 
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 * 
 * This file creation date: 15/08/2003 / 21:03:31
 * net.jforum.model.ConfigModel.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: ConfigModel.java,v 1.3 2004/06/01 19:47:29 pieter2 Exp $
 */
package net.jforum.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import net.jforum.entities.Config;
import net.jforum.util.preferences.SystemGlobals;

/**
 * Model interface for the {@link net.jforum.Config} class. 
 * 
 * @author Rafael Steil
 */
public class ConfigModel 
{
	/**
	 * Gets all entries in the database.
	 *  
	 * @return <code>ArrayList</code> with all entries configured
	 */
	public ArrayList getAllEntries()  throws IOException
	{
	    Iterator iter = SystemGlobals.fetchConfigKeyIterator();
		ArrayList l = new ArrayList();
	    while (iter.hasNext()) {
	        String key = (String) iter.next();
			Config config = new Config();
			config.setName(key);
			config.setValue(SystemGlobals.getValue(key));
			l.add(config);
	    }
	    
	    return l;
	}
	
	/**
	 * Updates some config entry
	 *  	 
	 * @param name The entry name
	 * @param value The entry value
	 * @throws IOException When the installation options cannot be written
	 */
	public void update(String name, String value) throws IOException
	{
	   if (SystemGlobals.getValue(name) != null) {
		   SystemGlobals.setValue(name, value);
		   SystemGlobals.saveInstallation();
	   }
	}
	
	/**
	 * Update a list of config entries
	 *  	 
	 * @param newValues The configuration entries to update and their new values
	 * @throws IOException When the installation options cannot be written
	 */
	public void update(Properties newValues) throws IOException
	{
		for (Iterator iter = newValues.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry entry = (Map.Entry)iter.next();
			
			SystemGlobals.setValue((String)entry.getKey(), (String)entry.getValue());
		}
		
		SystemGlobals.saveInstallation();
	}
}
