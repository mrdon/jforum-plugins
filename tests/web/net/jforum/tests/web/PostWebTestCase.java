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
 * This file creating date: 21.09.2004 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.tests.web;

import java.io.IOException;

import net.jforum.util.I18n;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

/**
 * @author Marc Wick
 * @version $Id: PostWebTestCase.java,v 1.4 2004/09/29 08:59:00 marcwick Exp $
 */
public class PostWebTestCase extends AbstractWebTestCase {

	public PostWebTestCase(String name) throws IOException {
		super(name);
	}

	public void testAnonymousPosting() {
		beginAt(FORUMS_LIST);
		clickLinkWithText("a test forum", 0);
		clickLinkWithImage("post.gif");
		setFormElement("subject", "subject of test posting");
		setFormElement("message", "message of test posting");
		submit("post");
	}

	public void testAnonymousReply() {
		beginAt(FORUMS_LIST);
		clickLinkWithText("a test forum", 0);
		clickLinkWithText("subject of test posting", 0);

		assertLinkNotPresentWithImage("icon_edit.gif");
		assertLinkNotPresentWithImage("icon_delete.gif");

		clickLinkWithImage("reply.gif");
		setFormElement("subject", "reply to test posting");
		setFormElement("message", "reply message to test posting");
		submit("post");
	}

	public void testAdminAnonymousPosting() {
		beginAt(FORUMS_LIST);
		login("Admin", "admin");
		clickLinkWithText("a test forum", 0);
		clickLinkWithText("subject of test posting", 0);

		assertLinkPresentWithImage("icon_edit.gif");
		assertLinkPresentWithImage("icon_delete.gif");

		clickLinkWithImage("icon_edit.gif");
		setFormElement("message", "postingEditedByAdmin");
		submit("post");
		assertTextPresent("postingEditedByAdmin");

		clickLinkWithImage("icon_delete.gif");
		assertTextNotPresent("postingEditedByAdmin");

		clickLinkWithText(I18n.getMessage(language, "ForumBase.logout"));
	}

	public void testNewDefaultUserPosting() {
		beginAt(FORUMS_LIST);
		login(UserWebTestCase.defaultTestuser, UserWebTestCase.password);
		clickLinkWithText("a test forum", 0);
		clickLinkWithImage("post.gif");
		setFormElement("subject", "defaultUser posting");
		setFormElement("message", "message of test posting");
		submit("post");
		clickLinkWithText(I18n.getMessage(language, "ForumBase.logout"));
	}

	public void testEditDefaultUserPosting() {
		beginAt(FORUMS_LIST);
		login(UserWebTestCase.defaultTestuser, UserWebTestCase.password);
		clickLinkWithText("a test forum", 0);
		clickLinkWithText("defaultUser posting", 0);

		assertLinkNotPresentWithImage("icon_delete.gif");

		clickLinkWithImage("icon_edit.gif");
		setFormElement("message", "edited message of test posting");
		submit("post");
		clickLinkWithText(I18n.getMessage(language, "ForumBase.logout"));
	}

	public void testWatchEmail() throws Exception {
		smtpServer = SimpleSmtpServer.start();
		beginAt(FORUMS_LIST);
		clickLinkWithText("a test forum", 0);
		clickLinkWithText("defaultUser posting", 0);
		clickLinkWithImage("reply.gif");
		setFormElement("subject", "reply to default user posting");
		setFormElement(
				"message",
				"reply message to default user posting, we are testing whether default user receives an email");
		submit("post");

		Thread.sleep(1000);
		assertEquals("topic watch email received", 1, smtpServer
				.getReceievedEmailSize());
		SmtpMessage mail = (SmtpMessage) smtpServer.getReceivedEmail().next();
		String body = mail.getBody();
		String link = body.substring(body.indexOf("http:"),
				body.indexOf(".page") + 5).trim();
		smtpServer.stop();

		getTestContext().setBaseUrl(link.substring(0, link.lastIndexOf('/')));
		gotoPage(link.substring(link.lastIndexOf('/')));
		assertTrue(
				"watch email received",
				getDialog()
						.getResponse()
						.getText()
						.indexOf(
								"reply message to default user posting, we are testing whether default user receives an email") > 0);
	}

	public void testSearchKeywords() {
		beginAt(FORUMS_LIST);
		clickLinkWithText(I18n.getMessage(language, "ForumBase.search"));
		setFormElement("search_keywords",
				"thisKeywordDoesNotExistAndShouldReturnZeroRows");
		submit();
		// we make sure no posting has been found
		assertTextPresent("0 "
				+ I18n.getMessage(language, "Search.recordsFound"));

		beginAt(FORUMS_LIST);
		clickLinkWithText(I18n.getMessage(language, "ForumBase.search"));
		setFormElement("search_keywords", "defaultUser");
		submit();
	}

	public void testSearchAuthor() {
		beginAt(FORUMS_LIST);
		clickLinkWithText(I18n.getMessage(language, "ForumBase.search"));
		setFormElement("search_author",
				"thisAuthorDoesNotExistAndShouldReturnZeroRows");
		submit();
		// we make sure no posting has been found
		assertTextPresent("0 "
				+ I18n.getMessage(language, "Search.recordsFound"));

		beginAt(FORUMS_LIST);
		clickLinkWithText(I18n.getMessage(language, "ForumBase.search"));
		setFormElement("search_author", "defaultUser");
		submit();
	}

}