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
 * $Id: ConfigModel.java,v 1.2 2004/04/21 23:57:24 rafaelsteil Exp $
 */
package net.jforum.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.jforum.entities.Config;
import net.jforum.util.SystemGlobals;

/**
 * Model interface for the {@link net.jforum.Config} class. 
 * 
 * @author Rafael Steil
 */
public class ConfigModel 
{
	
	/**
	 *Gets all entries in the database.
	 *  
	 * @return <code>ArrayList</code> with all entries configured
	 */
	public ArrayList getAllEntries()  throws IOException
	{
		Properties p = new Properties();
		p.load(new FileInputStream(SystemGlobals.getApplicationResourceDir() +"config/SystemGlobals.properties"));

		ArrayList l = new ArrayList();
		for (Iterator iter = p.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry)iter.next();
			
			Config config = new Config();
			config.setName(entry.getKey().toString());
			config.setValue(entry.getValue().toString());
			
			l.add(config);
		}
		
		return l;
	}
	
	/**
	 *Updates some config entry
	 *  	 
	 * @param name The entry name
	 * @param value The entry value
	 * @throws Exception
	 */
	public void update(String name, String value) throws IOException
	{
		Properties p = new Properties();
		p.load(new FileInputStream(SystemGlobals.getApplicationResourceDir() +"config/SystemGlobals.properties"));
		
		if (p.get(name) != null) {
			p.setProperty(name, value);
			
			p.store(new FileOutputStream(SystemGlobals.getApplicationResourceDir() +"config/SystemGlobals.properties", false), null);
		}		
	}
	
	public void update(Properties newValues) throws IOException
	{
		Properties p = new Properties();
		p.load(new FileInputStream(SystemGlobals.getApplicationResourceDir() +"config/SystemGlobals.properties"));
		
		for (Iterator iter = newValues.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry entry = (Map.Entry)iter.next();
			
			p.setProperty((String)entry.getKey(), (String)entry.getValue());
		}
		
		p.store(new FileOutputStream(SystemGlobals.getApplicationResourceDir() +"config/SystemGlobals.properties", false), null);
	}
}
