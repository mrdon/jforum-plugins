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
 * This file creating date: 20.09.2004 08:26:58
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.tests.web;

import java.util.Random;

import net.jforum.DBConnection;
import net.jforum.JForumCommonServlet;
import net.jforum.TestCaseUtils;
import net.jforum.util.I18n;
import net.jforum.util.mail.LostPasswordSpammer;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.view.forum.UserAction;

/**
 * @author Marc Wick
 * @author Rafael Steil
 * @version $Id: UserWebTestCase.java,v 1.17 2004/10/14 02:21:55 rafaelsteil Exp $
 */
public class UserWebTestCase extends AbstractWebTestCase {

    private static String lastTestuser;

    public static String defaultTestuser = "defaultTestuser";

    public static String password = "testpassword";

    public UserWebTestCase(String name) throws Exception {
        super(name);
    }

    public void testRegisterDefaultUser() {
        beginAt(FORUMS_LIST);
        assertLinkPresent("register");
        clickLink("register");
        assertFormPresent("formregister");
        setFormElement("username", defaultTestuser);
        setFormElement("email", defaultTestuser);
        setFormElement("password", password);
        setFormElement("password_confirm", password);
        submit();
    }

    public void testRegisterNewUser() {
        beginAt(FORUMS_LIST);
        assertLinkPresent("register");
        clickLink("register");
        assertFormPresent("formregister");
        lastTestuser = "testuser" + new Random().nextInt(1000000);
        setFormElement("username", lastTestuser);
        setFormElement("email", lastTestuser);
        setFormElement("password", "testpassword1");
        setFormElement("password_confirm", "testpassword1");
        submit();
        logout();
    }

    public void testChangePassword() {
        beginAt(FORUMS_LIST);
        login(lastTestuser, "testpassword1");
        assertLinkPresent("myprofile");
        clickLink("myprofile");
        setFormElement("current_password", "testpassword1");
        setFormElement("new_password", password);
        setFormElement("password_confirm", password);
        submit();
        logout();
    }

    public void testEditUserProfile() {
        login(lastTestuser, password);
        assertLinkPresent("myprofile");
        clickLink("myprofile");
        setFormElement("signature", "signature for testuser");
        submit();
        logout();
    }

    public void testPasswordForgottenEmail() throws Exception {
        passwordRecovery("email", lastTestuser.toUpperCase());
    }

    public void testPasswordForgottenUserName() throws Exception {
        passwordRecovery("username", lastTestuser);
    }
    
    private void toLostPasswordForm(String field, String value)
    {
    	beginAt(FORUMS_LIST);
        assertLinkPresent("login");
        clickLink("login");
        assertLinkPresent("lostpassword");
        clickLink("lostpassword");

        assertFormPresent("formlostpassword");
        setFormElement(field, value);
        submit();
    }
    
    public void testPasswordForgottenInvalidEmail()
    {
    	this.toLostPasswordForm("email", "IDontLikeEmails");
    	assertTextPresent(I18n.getMessage(language, "PasswordRecovery.invalidUserEmail"));
    }
    
    public void testPasswordForgottenInvalidUser()
    {
    	this.toLostPasswordForm("username", "johndoe");
    	assertTextPresent(I18n.getMessage(language, "PasswordRecovery.invalidUserEmail"));
    }
    
    public void testPasswordForgottenRecoverFormWithInvalidData()
    {
    	beginAt("/user/recoverPassword/abcdef123.page");
    	setFormElement("email", lastTestuser);
    	setFormElement("newPassword", "blah");
    	setFormElement("confirmPassword", "blah");
    	submit();

    	assertTextPresent(I18n.getMessage(language, "PasswordRecovery.invalidData"));
    }

    private void passwordRecovery(String field, String value) throws Exception {
        SystemGlobals.setValue(ConfigKeys.MAIL_SMTP_HOST, "localhost");
        SystemGlobals.setValue(ConfigKeys.BACKGROUND_TASKS, "false");
        SystemGlobals.saveInstallation();
        
        TestCaseUtils.initDatabaseImplementation();

        this.toLostPasswordForm(field, value);

        assertTextNotPresent(I18n.getMessage(language, "PasswordRecovery.invalidUserEmail"));
        assertTextPresent(I18n.getMessage(language, "PasswordRecovery.emailSent").substring(0, 20));
        
        // Check the message body
        JForumCommonServlet.DataHolder dh = new JForumCommonServlet.DataHolder();
        dh.setConnection(DBConnection.getImplementation().getConnection());
        JForumCommonServlet.setThreadLocalData(dh);
        
        String body;
        if ("email".equals(field)) {
        	body = new LostPasswordSpammer(new UserAction().prepareLostPassword(null, value), "").getMessageBody();
        }
        else {
        	body = new LostPasswordSpammer(new UserAction().prepareLostPassword(value, null), "").getMessageBody();
        }

        String link = body.substring(body.indexOf("http:"), body.indexOf(".page") + 5).trim();

        getTestContext().setBaseUrl(link.substring(0, link.lastIndexOf('/')));
        gotoPage(link.substring(link.lastIndexOf('/')));
        setFormElement("email", lastTestuser);
        setFormElement("newPassword", password);
        setFormElement("confirmPassword", password);
        submit();

        assertTextNotPresent(I18n.getMessage(language, "PasswordRecovery.invalidData"));
        assertTextPresent(I18n.getMessage(language, "PasswordRecovery.ok").substring(0, 20));
    }
}