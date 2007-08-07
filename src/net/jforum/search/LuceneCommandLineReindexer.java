/*
 * Copyright (c) JForum Team
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:
 * 
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
 * Created on 06/08/2007 15:27:53
 * 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.search;

import net.jforum.ConfigLoader;
import net.jforum.ForumStartup;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Rafael Steil
 * @version $Id: LuceneCommandLineReindexer.java,v 1.2 2007/08/07 13:30:50 rafaelsteil Exp $
 */
public class LuceneCommandLineReindexer
{
	private LuceneReindexArgs reindexerArgs;
	
	public static void main(String[] args)
	{
		System.out.println("****** INITIALIZING ");
		
		LuceneCommandLineReindexer reindexer = new LuceneCommandLineReindexer();
		reindexer.init(args);
		reindexer.start();
		
		System.out.println("****** FINISHED ");
	}
	
	private void start()
	{
		LuceneReindexer reindexer = new LuceneReindexer(
			(LuceneSettings)SystemGlobals.getObjectValue(ConfigKeys.LUCENE_SETTINGS),
			this.reindexerArgs, true);

		reindexer.startProcess();
	}
	
	private void init(String[] args)
	{
		this.parseCmdArgs(args);
		
		String appPath = "f:/eclipse-projects/jforum";
		
		DOMConfigurator.configure(appPath + "/WEB-INF/log4j.xml");
		
		ConfigLoader.startSystemglobals(appPath);
		
		SystemGlobals.loadQueries(SystemGlobals.getValue(ConfigKeys.SQL_QUERIES_GENERIC));
		SystemGlobals.loadQueries(SystemGlobals.getValue(ConfigKeys.SQL_QUERIES_DRIVER));
		
		ConfigLoader.createLoginAuthenticator();
		ConfigLoader.loadDaoImplementation();
		
		SearchFacade.init();
		
		ForumStartup.startDatabase();
	}
	
	private void parseCmdArgs(String[] args)
	{
		this.reindexerArgs = new LuceneReindexArgs(null, null, 1, 100, true, LuceneReindexArgs.TYPE_MESSAGE);
	}
}
