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

import java.util.Date;

/**
 * @author Marc Wick
 * @author Rafael Steil
 * @version $Id: AdminWebTestCase.java,v 1.9 2004/11/12 18:57:43 rafaelsteil Exp $
 */
public class AdminWebTestCase extends AbstractWebTestCase {
    public AdminWebTestCase(String name) throws Exception {
   		super(name);
    }
    
    /**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		this.adminLogin();
	}
    
    public void testCreateCategory() {
        // add category
        gotoFrame("leftFrame");
        
        assertLinkPresent("categories");
        clickLink("categories");
        gotoFrame("main");
        
        assertButtonPresent("btn_insert");
        clickButton("btn_insert");
        gotoFrame("main");
        
        assertFormPresent("form");
        setFormElement("category_name", "a test category");
        setFormElement("groups", "1");
        submit();
        
        // The createForum test is here because
        // we first need a category before adding
        // a forum
        this.createForum();
    }
    
    private void createForum() {
        // add forum
    	assertFramePresent("leftFrame");
        gotoFrame("leftFrame");
        
        assertLinkPresent("forums");
        clickLink("forums");
        gotoFrame("main");
        
        assertButtonPresent("btn_insert");
        clickButton("btn_insert");
        gotoFrame("main");
        
        assertFormPresent("form");
        
        setFormElement("forum_name", "a test forum");
        setFormElement("categories_id", "1");
        setFormElement("groups", "1");
        setFormElement("description", "a forum automatically generated for regression tests");
        submit();
        
        assertElementNotPresent("error");
    }
    
    public void testConfigurations() {
    	assertFramePresent("leftFrame");
        gotoFrame("leftFrame");
        clickLink("configurations");
        gotoFrame("main");
        setFormElement("p_forum.page.title", "jforum last run regression test " + new Date());
        setFormElement("p_i18n.board.default", language);

        // configure localhost as our mock smtp server (dumpster) is listening
        // on localhost
        setFormElement("p_mail.sender", "regressiontest@jforum.net");
        // we set the smtp server to localhost to be able to test the emails
        // afterwards
        setFormElement("p_mail.smtp.host", "localhost");
        // we don't use authentication as dumpster does not support it
        setFormElement("p_mail.smtp.auth", "false");
        submit();

        gotoFrame("leftFrame");
        clickLink("configurations");
        gotoFrame("main");

        assertFormElementEquals("p_mail.smtp.auth", "false");
    }
    
    private void adminLogin() {
    	beginAt(FORUMS_LIST);
        login("Admin", "admin", true);
    	clickLink("adminpanel");
    }

    private void adminLogout() {
        gotoFrame("leftFrame");
        assertLinkPresent("forumIndex");
        clickLink("forumIndex");
        assertLinkPresent("logout");
        clickLink("logout");
    }
}