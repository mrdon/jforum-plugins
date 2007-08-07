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

import jargs.gnu.CmdLineParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.jforum.ConfigLoader;
import net.jforum.ForumStartup;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Rafael Steil
 * @version $Id: LuceneCommandLineReindexer.java,v 1.3 2007/08/07 15:02:29 rafaelsteil Exp $
 */
public class LuceneCommandLineReindexer
{
	private LuceneReindexArgs reindexerArgs;
	private boolean recreate;
	private String path;
	
	public static void main(String[] args)
	{
		LuceneCommandLineReindexer reindexer = new LuceneCommandLineReindexer();
		reindexer.init(args);
		
		System.out.println("*** INITIALIZING \n");
		System.out.println("** At any time, press CTRL+C to stop the process \n");
		
		reindexer.start();
		
		System.out.println("*** FINISHED ");
	}
	
	private void start()
	{
		LuceneReindexer reindexer = new LuceneReindexer(
			(LuceneSettings)SystemGlobals.getObjectValue(ConfigKeys.LUCENE_SETTINGS),
			this.reindexerArgs, this.recreate);

		reindexer.startProcess();
	}
	
	private void init(String[] args)
	{
		this.parseCmdArgs(args);
		
		DOMConfigurator.configure(this.path + "/WEB-INF/log4j.xml");
		
		ConfigLoader.startSystemglobals(this.path);
		
		SystemGlobals.loadQueries(SystemGlobals.getValue(ConfigKeys.SQL_QUERIES_GENERIC));
		SystemGlobals.loadQueries(SystemGlobals.getValue(ConfigKeys.SQL_QUERIES_DRIVER));
		
		ConfigLoader.createLoginAuthenticator();
		ConfigLoader.loadDaoImplementation();
		
		SearchFacade.init();
		
		ForumStartup.startDatabase();
	}
	
	private void parseCmdArgs(String[] args)
	{
		StringBuffer description = new StringBuffer(512);
		description.append("\n*** Going to reindex using the following options: \n");
		
		CmdLineParser parser = new CmdLineParser();
		
		CmdLineParser.Option recreateOption = parser.addBooleanOption("recreateIndex");
		CmdLineParser.Option typeOption = parser.addStringOption("type");
		CmdLineParser.Option pathOption = parser.addStringOption("path");
		CmdLineParser.Option firstPostIdOption = parser.addIntegerOption("firstPostId");
		CmdLineParser.Option lastPostIdOption = parser.addIntegerOption("lastPostId");
		CmdLineParser.Option fromDateOption = parser.addStringOption("fromDate");
		CmdLineParser.Option toDateOption = parser.addStringOption("toDate");
		CmdLineParser.Option avoidDuplicatedOption = parser.addBooleanOption("avoidDuplicatedRecords");
		
		try {
			parser.parse(args);
		}
		catch (CmdLineParser.OptionException e) {
			System.out.println(e.getMessage());
			this.printUsage();
		}
		
		if (parser.getRemainingArgs().length > 0) {
			this.printUsage();
		}
		
		// Type
		String type = (String)parser.getOptionValue(typeOption);
		
		if (StringUtils.isEmpty(type) || (!type.equals("date") && !type.equals("message"))) {
			System.out.println("*** --type should be either date or message");
			this.printUsage();
		}
		
		description.append("\t-> Searching by ").append(type).append('\n');
		
		// Path
		this.path = (String)parser.getOptionValue(pathOption);
		
		if (StringUtils.isEmpty(this.path)) {
			System.out.println("*** --path is a required option. It should point to the root directory where JForum is installed");
			this.printUsage();
		}
		
		description.append("\t->App path: ").append(path).append('\n');
		
		// FirstPostId and LastPostId
		int firstPostId = ((Integer)parser.getOptionValue(firstPostIdOption, new Integer(0))).intValue();
		int lastPostId = ((Integer)parser.getOptionValue(lastPostIdOption, new Integer(0))).intValue();
		
		if ("message".equals(type)) {
			if (firstPostId == 0 || lastPostId == 0 || lastPostId <= firstPostId) {
				System.out.println("*** --firstPostId and --lastPostId are required fields when --type=message. "
					+ "Also, --lastPostId should be greater than --firstPostId");
				this.printUsage();
			}
			
			description.append("\t-> From Post #").append(firstPostId).append(" to Post #").append(lastPostId).append('\n');
		}
		
		// FromDate and ToDate
		Date fromDate = null;
		Date toDate = null;
		
		if ("date".equals(type)) {
			fromDate = this.parseDate((String)parser.getOptionValue(fromDateOption));
			toDate = this.parseDate((String)parser.getOptionValue(toDateOption));
			
			if (fromDate == null || toDate == null) {
				System.out.println("*** --fromDate and --toDate are required fields when --type=date");
				this.printUsage();
			}
			
			description.append("\t-> From date ").append(fromDate).append(" to ").append(toDate).append('\n');
		}
		
		// Recreate
		this.recreate = ((Boolean)parser.getOptionValue(recreateOption, Boolean.FALSE)).booleanValue();
		description.append("\t->Recreate the index? ").append(this.recreate ? "Yes" : "No").append('\n');
		
		// AvoidDuplicatedRecords
		boolean avoidDuplicated = ((Boolean)parser.getOptionValue(avoidDuplicatedOption, Boolean.FALSE)).booleanValue();
		description.append("\t->Avoid duplicated records? ").append(avoidDuplicated ? "Yes" : "No").append('\n');
		
		this.reindexerArgs = new LuceneReindexArgs(fromDate, toDate, firstPostId, 
			lastPostId, avoidDuplicated, 
			"date".equals(type) ? LuceneReindexArgs.TYPE_DATE : LuceneReindexArgs.TYPE_MESSAGE);
		
		System.out.println(description);
	}
	
	private Date parseDate(String s)
	{
		Date date = null;
		
		if (!StringUtils.isEmpty(s)) {
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(s);
			}
			catch (ParseException e) { }
		}
		
		return date;
	}

	private void printUsage()
	{
		System.out.println("\nUsage: LuceneCommandLineReindexer \n"
			+ " --path full_path_to_JForum_root_directory \n"
			+ " --type {date|message} \n"
			+ " --firstPostId a_id \n"
			+ " --lastPostId a_id \n"
			+ " --fromDate dd/MM/yyyy \n"
			+ " --toDate dd/MM/yyyy \n"
			+ " [--recreateIndex]" 
			+ " [--avoidDuplicatedRecords]");
		System.exit(1);
	}
}

