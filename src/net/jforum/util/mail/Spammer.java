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
 * This file creation date: 03/03/2004 - 20:29:45
 * net.jforum.util.mail.Spammer.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: Spammer.java,v 1.2 2004/04/21 23:57:38 rafaelsteil Exp $
 */
package net.jforum.util.mail;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

import net.jforum.util.SystemGlobals;

/**
 * Dispatch emails to the world. 
 * TODO: should do some refactoring to send a personalized email to each user. 
 * 
 * @author Rafael Steil
 */
public class Spammer 
{
	private static int MESSAGE_HTML = 0;
	private static int MESSAGE_TEXT = 1;
	
	private Properties mailProps = new Properties();
	private static Spammer instance = new Spammer(true);
	private static int messageFormat;
	private static Session session;
	private static String username;
	private static String password;
	
	private Message message;
	
	public Spammer() {}

	// Trick ;)
	private Spammer(boolean init) throws EmailException
	{
		mailProps.put("mail.smtp.host", (String)SystemGlobals.getValue("mail.smtp.host"));
		mailProps.put("mail.mime.address.strict", "false");
		mailProps.put("mail.mime.charset", (String)SystemGlobals.getValue("mail.charset"));
		mailProps.put("mail.smtp.auth", (String)SystemGlobals.getValue("mail.smtp.auth"));
		
		username = (String)SystemGlobals.getValue("mail.smtp.username");
		password = (String)SystemGlobals.getValue("mail.smtp.password");
		
		messageFormat = SystemGlobals.getValue("mail.messageFormat").toString().equals("html") ? MESSAGE_HTML : MESSAGE_TEXT;

		session = Session.getDefaultInstance(mailProps);
	}

	public static Session getSession()
	{
		return session;
	}
	
	public final Message getMesssage()
	{
		return this.message;
	}
	
	public boolean dispatchMessages() throws Exception
	{
		if (SystemGlobals.getValue("mail.smtp.auth").toString().equals("true")) {
			if (username != null && !username.equals("") && password != null && !password.equals("")) {
				Transport transport = Spammer.getSession().getTransport("smtp");
				
				try {
					transport.connect((String)SystemGlobals.getValue("mail.smtp.host"), username, password);
				}
				catch (MessagingException e) {
					throw new EmailException("Could not connect to the mail server", e);
				}
				
				if (transport.isConnected()) {
					Address[] addresses = message.getAllRecipients();
					for (int i = 0; i < addresses.length; i++) {
						// Tricks and tricks
						message.setRecipient(Message.RecipientType.TO, addresses[i]);
						transport.sendMessage(message, new Address[] { addresses[i] });
					}
				}
			}
		}
		else {
			Address[] addresses = message.getAllRecipients();
			for (int i = 0; i < addresses.length; i++) {
				message.setRecipient(Message.RecipientType.TO, addresses[i]);
				Transport.send(message, new Address[] { addresses[i] });
			}
		}
		
		return true;
	}
	
	protected final void prepareMessage(ArrayList addresses, SimpleHash params, String subject, String messageFile) throws EmailException
	{
		this.message = new MimeMessage(session);
		
		try {
			InternetAddress[] recipients = new InternetAddress[addresses.size()];

			this.message.setFrom(new InternetAddress((String)SystemGlobals.getValue("mail.sender")));
			this.message.setSubject(subject);
			
			StringWriter sWriter = new StringWriter();
			Template template = Configuration.getDefaultConfiguration().getTemplate(messageFile);
			template.process(params, sWriter);
			
			if (messageFormat == MESSAGE_HTML) {
				this.message.setContent(sWriter.toString(), "text/html");
			}
			else {
				this.message.setText(sWriter.toString());
			}
			
			int i = 0;
			for (Iterator iter = addresses.iterator(); iter.hasNext(); i++) {
				recipients[i] = new InternetAddress((String)iter.next());
			}
			
			this.message.setRecipients(Message.RecipientType.TO, recipients);
		}
		catch (Exception e) {
			throw new EmailException(e);
		}
	}
}
