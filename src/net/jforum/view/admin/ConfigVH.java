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
 * This file creation date: 15/08/2003 / 20:56:33
 * net.jforum.view.admin.ConfigVH.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: ConfigVH.java,v 1.3 2004/06/01 19:47:21 pieter2 Exp $
 */
package net.jforum.view.admin;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.model.ConfigModel;
import net.jforum.util.preferences.SystemGlobals;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Rafael Steil
 */
public class ConfigVH extends Command 
{
	//	Listing
	 public void list() throws Exception
	 {
	 	Properties p = new Properties();
	 	Iterator iter = SystemGlobals.fetchConfigKeyIterator();
	 	while (iter.hasNext()) {
	 	    String key = (String) iter.next();
	 	    String value = SystemGlobals.getValue(key);
	 	    p.put(key, value);
	 	}
	 	
	 	Properties locales = new Properties();
	 	locales.load(new FileInputStream(SystemGlobals.getApplicationResourceDir() +"/config/languages/locales.properties"));
	 	ArrayList localesList = new ArrayList();
	 	
	 	for (Enumeration e = locales.keys(); e.hasMoreElements(); ) {
	 		localesList.add(e.nextElement());
	 	}
	 	
	 	JForum.getContext().put("config", p);
	 	JForum.getContext().put("locales", localesList);
		JForum.getContext().put("moduleAction", "config_list.htm");	
	 }
	 
	 public void editSave() throws Exception
	 {
	 	Properties p = new Properties();
	 	
	 	Enumeration e = JForum.getRequest().getParameterNames();
	 	while (e.hasMoreElements()) {
	 		String name = (String)e.nextElement();
	 		
	 		if (name.startsWith("p_")) {
	 			p.setProperty(name.substring(name.indexOf('_') + 1), JForum.getRequest().getParameter(name));
	 		}
	 	}
	 	
	 	new ConfigModel().update(p);
	 	this.list();
	 }

	/* 
	 * @see net.jforum.Command#process()
	 */
	public Template process() throws Exception 
	{
		if (AdminVH.isAdmin()) {
			super.process();
		}

		return Configuration.getDefaultConfiguration().getTemplate("admin/index_main.htm");
	}

}
