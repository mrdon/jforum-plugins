/*
 * Copyright (c) 2004, Rafael Steil
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
 * This file creating date: 20.09.2004 16:13:52
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.tests.web;

import java.io.IOException;

import com.dumbster.smtp.SimpleSmtpServer;

import net.jforum.util.I18n;
import net.jforum.util.preferences.SystemGlobals;
import net.sourceforge.jwebunit.WebTestCase;

/**
 * @author Marc Wick
 * @version $Id: AbstractWebTestCase.java,v 1.1 2004/09/21 16:00:13 rafaelsteil
 *          Exp $
 */
public abstract class AbstractWebTestCase extends WebTestCase {
	public static class SimpleHTMLParserListener implements
			com.meterware.httpunit.parsing.HTMLParserListener {

		public void error(java.net.URL url, java.lang.String msg, int line,
				int column) {
			System.err.println("error : " + url + " " + msg + " " + line + " "
					+ column);
		}

		public void warning(java.net.URL url, java.lang.String msg, int line,
				int column) {
			System.err.println("warning : " + url + " " + msg + " " + line
					+ " " + column);
		}
	}

	protected String language = "de_DE";

	protected SimpleSmtpServer smtpServer;

	public AbstractWebTestCase(String name) throws IOException {
		super(name);
		init();
		getTestContext().setBaseUrl("http://localhost:8080/jforum/forums");
		//HTMLParserFactory.setParserWarningsEnabled(true);
		//HTMLParserFactory.addHTMLParserListener(new
		// SimpleHTMLParserListener());
	}

	private void init() throws IOException {
		String appPath = "c:/marc/java/jakarta-tomcat-5.0.19/webapps/JForum-RC5";
		SystemGlobals.initGlobals(appPath, appPath
				+ "/WEB-INF/config/SystemGlobals.properties", null);
		SystemGlobals.loadDefaults();
		I18n.load();
	}

	protected void login(String username, String password) {
		beginAt("/list.page");
		clickLinkWithText(I18n.getMessage(language, "ForumBase.login"));
		setFormElement("username", username);
		setFormElement("password", password);
		submit();
	}

}