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

import java.io.IOException;
import java.util.Random;

import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

/**
 * @author Marc Wick
 * @version $Id: UserWebTestCase.java,v 1.13 2004/10/04 15:23:17 marcwick Exp $
 */
public class UserWebTestCase extends AbstractWebTestCase {

    private static String lastTestuser;

    public static String defaultTestuser = "defaultTestuser";

    public static String password = "testpassword";

    public UserWebTestCase(String name) throws IOException {
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

    public void testPasswordForgottenUserName() throws Exception {
        SystemGlobals.setValue(ConfigKeys.MAIL_SMTP_HOST, "localhost");
        SystemGlobals.saveInstallation();

        // start smtp server on localhost to receive and verify test emails
        smtpServer = SimpleSmtpServer.start();

        beginAt(FORUMS_LIST);
        assertLinkPresent("login");
        clickLink("login");
        assertLinkPresent("lostpassword");
        clickLink("lostpassword");

        assertFormPresent("formlostpassword");
        setFormElement("username", lastTestuser);
        submit();

        // give the jforum servlet time to deliver the email to the smtp server
        waitForEmail();

        // test if an email has been received by localhost
        assertEquals("password lost email received", 1, smtpServer.getReceievedEmailSize());

        // now test the email
        SmtpMessage mail = (SmtpMessage) smtpServer.getReceivedEmail().next();
        String body = mail.getBody();
        String link = body.substring(body.indexOf("http:"), body.indexOf(".page") + 5).trim();

        smtpServer.stop();

        System.out.println(link);

        getTestContext().setBaseUrl(link.substring(0, link.lastIndexOf('/')));
        gotoPage(link.substring(link.lastIndexOf('/')));
        setFormElement("email", lastTestuser);
        setFormElement("newPassword", password);
        setFormElement("confirmPassword", password);
        submit();

        dumpResponse(System.out);

        clickLinkWithText(I18n.getMessage(language, "ForumBase.logout"));
    }
}