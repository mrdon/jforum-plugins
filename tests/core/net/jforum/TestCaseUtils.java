/*
 * Copyright (c) 2003, Rafael Steil
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
 * This file creation date: 29/09/2004 - 18:16:46
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum;

import java.io.IOException;

import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * General utilities for the test cases.
 * 
 * @author Rafael Steil
 * @version $Id: TestCaseUtils.java,v 1.1 2004/09/29 21:36:29 rafaelsteil Exp $
 */
public class TestCaseUtils
{
	private static TestCaseUtils utils = new TestCaseUtils();
	private String rootDir;
	private String language;
	
	private TestCaseUtils() {}
	
	public static void loadEnvironment() throws Exception
	{
		utils.init();
	}
	
	private void init() throws IOException 
	{
		this.rootDir = this.getClass().getResource("/").getPath();
		this.rootDir = this.rootDir.substring(0, utils.rootDir.length()
						- "/WEB-INF/classes".length());

		SystemGlobals.initGlobals(this.rootDir, this.rootDir
				+ "/WEB-INF/config/SystemGlobals.properties", null);

		SystemGlobals.loadDefaults();
		this.language = SystemGlobals.getValue(ConfigKeys.I18N_DEFAULT);
		I18n.load();
	}
}
