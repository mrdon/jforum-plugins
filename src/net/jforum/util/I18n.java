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
 * This file creation date: Mar 10, 2003 / 9:28:40 PM
 * net.jforum.util.I18n.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: I18n.java,v 1.3 2004/06/01 19:47:27 pieter2 Exp $
 */
package net.jforum.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Properties;

import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * I18n (Internacionalization) class implementation. 
 * Does nothing of special, just loads the messages into 
 * memory and provides a static method to acess them.
 *  
 * @author Rafael Steil
 */
public class I18n 
{
	private static I18n classInstance = new I18n();
	private static HashMap messagesMap = new HashMap();
	private static Properties localeNames = new Properties();
	private static String defaultName;
	private static String baseDir;
	
	public static final String CANNOT_DELETE_GROUP = "CannotDeleteGroup";
	public static final String CANNOT_DELETE_CATEGORY = "CannotDeleteCategory";
	
	private I18n() {} 
	
	/**
	 * Gets the singleton
	 * 
	 * @return Instance of I18n class
	 */
	public static I18n getInstance()
	{
		return classInstance;
	}
	
	/**
	 * Load the default I18n file
	 * 
	 * @throws IOException
	 */
	public static synchronized void load() throws IOException
	{
		baseDir = SystemGlobals.getApplicationResourceDir() +"/config/languages/";
		localeNames.load(new FileInputStream(baseDir +"locales.properties"));
		defaultName = SystemGlobals.getValue(ConfigKeys.I18N_DEFAULT);
		load(defaultName);
	}
	
	public static void load(String localeName) throws IOException
	{
		Properties p = new Properties();
		p.load(new FileInputStream(baseDir + localeNames.getProperty(localeName)));
		
		messagesMap.put(localeName, p);
	}
	
	/**
	 * Gets a I18N (internationalized) message.
	 * 
	 * @param localeName The locale name to retrieve the messages from
	 * @param messageName The message name to retrieve. Must be a valid entry into the file 
	 * specified by <code>i18n.file</code> property.
	 * @param params Parameters needed by some messages. The messages with extra parameters are 
	 * formated according to {@link java.text.MessageFormat} specification
	 * @return String With the message
	 */
	public static String getMessage(String localeName, String messageName, Object params[])
	{
		return MessageFormat.format(((Properties)messagesMap.get(localeName)).getProperty(messageName), params);
	}

	/**
	 * @see #getMessage(String, String, Object[]) 
	 */
	public static String getMessage(String messageName, Object params[])
	{
		return getMessage(defaultName, messageName, params);
	}
	
	/**
	 * Gets an I18N (internationalization) message.
	 * 
	 * @param m The message name to retrieve. Must be a valid entry into the file 
	 * specified by <code>i18n.file</code> property.
	 * @return String With the message
	 */
	public static String getMessage(String localeName, String m)
	{
		return (((Properties)messagesMap.get(localeName)).getProperty(m));
	}	
	
	public static String getMessage(String m)
	{
		return getMessage(defaultName, m);
	}
}
