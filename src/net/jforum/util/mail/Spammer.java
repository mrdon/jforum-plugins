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
 * This file creation date: 03/03/2004 - 20:29:45
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.mail;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.jforum.JForumExecutionContext;
import net.jforum.entities.User;
import net.jforum.exceptions.MailException;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * Dispatch emails to the world. 
 * 
 * @author Rafael Steil
 * @version $Id: Spammer.java,v 1.27 2006/10/04 02:51:12 rafaelsteil Exp $
 */
public class Spammer
{
	private static final Logger logger = Logger.getLogger(Spammer.class);

	private static int MESSAGE_HTML = 0;
	private static int MESSAGE_TEXT = 1;
	
	private static int messageFormat;
	private static Session session;
	private static String username;
	private static String password;
	
	private Properties mailProps = new Properties();
	private MimeMessage message;
	private List users = new ArrayList();
	private String messageText;
	private String messageId;
	private String inReplyTo;
	
	protected Spammer() throws MailException
	{
		boolean ssl = SystemGlobals.getBoolValue(ConfigKeys.MAIL_SMTP_SSL);
		
		String hostProperty = this.hostProperty(ssl);
		String portProperty = this.portProperty(ssl);
		String authProperty = this.authProperty(ssl);
		String localhostProperty = this.localhostProperty(ssl);
		
		mailProps.put(hostProperty, SystemGlobals.getValue(ConfigKeys.MAIL_SMTP_HOST));
		mailProps.put(portProperty, SystemGlobals.getValue(ConfigKeys.MAIL_SMTP_PORT));

		String localhost = SystemGlobals.getValue(ConfigKeys.MAIL_SMTP_LOCALHOST);
		
		if (!StringUtils.isEmpty(localhost)) {
			mailProps.put(localhostProperty, localhost);
		}
		
		mailProps.put("mail.mime.address.strict", "false");
		mailProps.put("mail.mime.charset", SystemGlobals.getValue(ConfigKeys.MAIL_CHARSET));
		mailProps.put(authProperty, SystemGlobals.getValue(ConfigKeys.MAIL_SMTP_AUTH));

		username = SystemGlobals.getValue(ConfigKeys.MAIL_SMTP_USERNAME);
		password = SystemGlobals.getValue(ConfigKeys.MAIL_SMTP_PASSWORD);

		messageFormat = SystemGlobals.getValue(ConfigKeys.MAIL_MESSSAGE_FORMAT).equals("html") 
			? MESSAGE_HTML
			: MESSAGE_TEXT;

		session = Session.getDefaultInstance(mailProps);
	}

	public boolean dispatchMessages()
	{
        try
        {
            if (SystemGlobals.getBoolValue(ConfigKeys.MAIL_SMTP_AUTH)) {
                if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                	boolean ssl = SystemGlobals.getBoolValue(ConfigKeys.MAIL_SMTP_SSL);
                	
                    Transport transport = Spammer.getSession().getTransport(ssl ? "smtps" : "smtp");
                    
                    try {
	                    String host = SystemGlobals.getValue(ConfigKeys.MAIL_SMTP_HOST);
	                    transport.connect(host, username, password);
	
	                    if (transport.isConnected()) {
	                        for (Iterator userIter = this.users.iterator(); userIter.hasNext(); ) {
	                        	User user = (User)userIter.next();
	                        	
	                        	Address address = new InternetAddress(user.getEmail());
	                        	
	                        	message.setRecipient(Message.RecipientType.TO, address);
	                            transport.sendMessage(message, new Address[] { address });
	                        }
	                    }
                    }
                    catch (Exception e) {
                    	throw new MailException(e);
                    }
                    finally {
                    	try { transport.close(); }
                    	catch (Exception e) {}
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
        }
        catch (MessagingException e) {
            throw new MailException("Error while dispatching the message.", e);
        }

        return true;
	}

	/**
	 * Prepares the mail message for sending.
	 * 
	 * @param addresses The list of email addresses that will receive the notification
	 * @param params the parameters to pass to the mail message template
	 * @param subject the subject of the email
	 * @param messageFile the path to the mail message template
	 * @throws MailException
	 */
	protected void prepareMessage(SimpleHash params, String subject, String messageFile)
			throws MailException
	{
		if (this.messageId != null) {
			this.message = new IdentifiableMimeMessage(session);
			((IdentifiableMimeMessage)this.message).setMessageId(this.messageId);
		}
		else {
			this.message = new MimeMessage(session);
		}
		
		params.put("forumName", SystemGlobals.getValue(ConfigKeys.FORUM_NAME));

		try {
			String charset = SystemGlobals.getValue(ConfigKeys.MAIL_CHARSET);

			this.message.setSentDate(new Date());
			this.message.setFrom(new InternetAddress(SystemGlobals.getValue(ConfigKeys.MAIL_SENDER)));
			this.message.setSubject(subject, charset);
			
			if (this.inReplyTo != null) {
				this.message.addHeader("In-Reply-To", this.inReplyTo);
			}

			this.messageText = this.getMessageText(params, messageFile);

			if (messageFormat == MESSAGE_HTML) {
				this.message.setContent(this.messageText, "text/html; charset=" + charset);
			}
			else {
				this.message.setText(this.messageText, charset);
			}
		}
		catch (Exception e) {
			throw new MailException(e);
		}
	}
	
	protected void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}
	
	protected void setInReplyTo(String inReplyTo)
	{
		this.inReplyTo = inReplyTo;
	}
	
	protected void setUsers(List users)
	{
		this.users = users;
	}
	
	/**
	 * Gets the message text to send in the email.
	 * 
	 * @param params The optional params. If no need of any, just pass null
	 * @param messageFile The optional message file to load the text. 
	 * @return The email message text
	 * @throws Exception
	 */
	protected String getMessageText(SimpleHash params, String messageFile) throws Exception
	{
		String templateEncoding = SystemGlobals.getValue(ConfigKeys.MAIL_TEMPLATE_ENCODING);
		StringWriter sWriter = new StringWriter();
		Template template;

		if (templateEncoding == null || "".equals(templateEncoding.trim())) {
			template = JForumExecutionContext.templateConfig().getTemplate(messageFile);
		}
		else {
			template = JForumExecutionContext.templateConfig().getTemplate(messageFile, templateEncoding);
		}
		
		template.process(params, sWriter);
		
		return sWriter.toString();
	}

	/**
	 * Gets the email body
	 * @return String with the email body that will be sent to the user
	 */
	public String getMessageBody()
	{
		return this.messageText;
	}

	private String localhostProperty(boolean ssl)
	{
		return ssl 
			? ConfigKeys.MAIL_SMTP_SSL_LOCALHOST
			: ConfigKeys.MAIL_SMTP_LOCALHOST;
	}

	private String authProperty(boolean ssl)
	{
		return ssl 
			? ConfigKeys.MAIL_SMTP_SSL_AUTH
			: ConfigKeys.MAIL_SMTP_AUTH;
	}

	private String portProperty(boolean ssl)
	{
		return ssl 
			? ConfigKeys.MAIL_SMTP_SSL_PORT
			: ConfigKeys.MAIL_SMTP_PORT;
	}

	private String hostProperty(boolean ssl)
	{
		return ssl 
			? ConfigKeys.MAIL_SMTP_SSL_HOST
			: ConfigKeys.MAIL_SMTP_HOST;
	}
	
	public static Session getSession()
	{
		return session;
	}

	public final Message getMesssage()
	{
		return this.message;
	}
}
