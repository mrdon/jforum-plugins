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
 * This file creating date: 20.09.2004 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.tests.web;

import java.io.IOException;
import java.util.Date;

import net.jforum.util.I18n;

/**
 * @author Marc Wick
 * @version $Id: AdminWebTestCase.java,v 1.2 2004/09/22 13:43:23 marcwick Exp $
 */
public class AdminWebTestCase extends AbstractWebTestCase {

	public AdminWebTestCase(String name) throws IOException {
		super(name);
	}

	public void testSetupCategoriesAndForums() {
		beginAt("/list.page");
		login("Admin", "admin");
		clickLinkWithText("Admin Control Panel");

		// add category
		gotoFrame("leftFrame");
		clickLinkWithText("Categories");
		gotoFrame("main");
		clickButton("btn_insert");
		gotoFrame("main");
		setFormElement("category_name", "a test category");
		setFormElement("groups", "1");
		submit();

		// add forum
		gotoFrame("leftFrame");
		clickLinkWithText("Forums");
		gotoFrame("main");
		clickButton("btn_insert");
		gotoFrame("main");
		setFormElement("forum_name", "a test forum");
		setFormElement("categories_id", "0");
		setFormElement("groups", "1");
		setFormElement("description",
				"a forum automatically generated for regression tests");
		submit();

		gotoFrame("leftFrame");
		clickLinkWithText("Configurations");
		gotoFrame("main");
		setFormElement("p_forum.page.title", "jforum last run regression test "
				+ new Date());
		setFormElement("p_i18n.default.admin", language);
		setFormElement("p_mail.smtp.auth", "false");
		submit();

		adminLogout();
	}

	/**
	 * bug in rc5 requires to save permission of all groups and users The
	 * workaround does not solve the problem.
	 *  
	 */
	public void testWorkaround() {
		beginAt("/list.page");
		login("Admin", "admin");
		clickLinkWithText("Admin Control Panel");
		gotoFrame("leftFrame");
		clickLinkWithText("Groups");
		gotoFrame("main");
		clickLinkWithText(I18n.getMessage(language, "Permissions"), 0);
		gotoFrame("main");
		submit();
		clickLinkWithText(I18n.getMessage(language, "Permissions"), 1);
		gotoFrame("main");
		submit();
		clickLinkWithText(I18n.getMessage(language, "Permissions"), 2);
		gotoFrame("main");
		submit();

		gotoFrame("leftFrame");
		clickLinkWithText("Users");
		gotoFrame("main");
		clickLinkWithText(I18n.getMessage(language, "Permissions"), 0);
		gotoFrame("main");
		setFormElement("perm_administration$single", "allow");
		setFormElement("perm_forum", "all");
		setFormElement("perm_anonymous_post", "all");
		setFormElement("perm_create_sticky_announcement_topics$single", "allow");
		setFormElement("perm_read_only_forums", "all");
		setFormElement("perm_moderation$single", "allow");
		setFormElement("perm_moderation_forums", "all");
		setFormElement("perm_moderation_post_remove$single", "allow");
		setFormElement("perm_moderation_post_edit$single", "allow");
		setFormElement("perm_moderation_topic_move$single", "allow");
		setFormElement("perm_moderation_topic_lockUnlock$single", "allow");
		setFormElement("perm_read_only_forums", "all");
		submit();
		clickLinkWithText(I18n.getMessage(language, "Permissions"), 1);
		gotoFrame("main");
		setFormElement("perm_administration$single", "deny");
		setFormElement("perm_forum", "all");
		setFormElement("perm_anonymous_post", "all");
		setFormElement("perm_create_sticky_announcement_topics$single", "allow");
		setFormElement("perm_read_only_forums", "all");
		setFormElement("perm_moderation$single", "allow");
		setFormElement("perm_moderation_forums", "all");
		setFormElement("perm_moderation_post_remove$single", "allow");
		setFormElement("perm_moderation_post_edit$single", "allow");
		setFormElement("perm_moderation_topic_move$single", "allow");
		setFormElement("perm_moderation_topic_lockUnlock$single", "allow");
		setFormElement("perm_read_only_forums", "all");
		submit();

		adminLogout();
	}

	private void adminLogout() {
		gotoFrame("leftFrame");
		clickLinkWithText("Forum Index");
		gotoFrame("main");
		clickLinkWithText(I18n.getMessage(language, "ForumBase.logout"));
	}
}