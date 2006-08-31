/*
 * Created on 30/08/2006 22:09:25
 */
package net.jforum.api.integration.mail.pop;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import net.jforum.exceptions.MailException;

/**
 * @author Rafael Steil
 * @version $Id: POPConnectorMock.java,v 1.1 2006/08/31 02:12:22 rafaelsteil Exp $
 */
public class POPConnectorMock extends POPConnector
{
	public Message[] listMessages()
	{
		try {
			Message[] messages = {
				new MessageMock(null, new ByteArrayInputStream(new String("Message 1").getBytes()))
			};
			
			return messages;
		}
		catch (Exception e) {
			throw new MailException(e);
		}
	}
	
	public void openConnection() {}
	public void closeConnection() {}
	
	private static class MessageMock extends MimeMessage
	{
		public MessageMock(Session session, InputStream is) throws MessagingException
		{
			super(session, is);
		}
	}
} 
