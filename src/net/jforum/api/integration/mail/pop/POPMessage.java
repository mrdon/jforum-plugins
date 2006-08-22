/*
 * Created on 21/08/2006 22:14:04
 */
package net.jforum.api.integration.mail.pop;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;

/**
 * Represents a pop message. 
 * @author Rafael Steil
 * @version $Id: POPMessage.java,v 1.1 2006/08/22 02:05:25 rafaelsteil Exp $
 */
public class POPMessage
{
	private static final String IN_REPLY_TO = "In-Reply-To";
	private static final String REFERENCES = "References";
	
	private String subject;
	private Object message;
	private String sender;
	private String replyTo;
	private String references;
	private String inReplyTo;
	private String contentType;
	private Date sendDate;
	private Map headers;
	
	/**
	 * Creates a new instance based on a {@link Message}
	 * @param message the message to convert from.
	 */
	public POPMessage(Message message)
	{
		this.extract(message);
	}
	
	/**
	 * Given a {@link Message}, converts it to our internal format
	 * @param message the message to convert
	 */
	private void extract(Message message)
	{
		try {
			this.subject = message.getSubject();
			this.message = message.getContent();
			this.contentType = message.getContentType();
			this.replyTo = ((InternetAddress)message.getReplyTo()[0]).getAddress();
			this.sender = ((InternetAddress)message.getFrom()[0]).getAddress();
			
			this.headers = new HashMap();
			
			for (Enumeration e = message.getAllHeaders(); e.hasMoreElements(); ) {
				Header header = (Header)e.nextElement();
				this.headers.put(header.getName(), header.getValue());
			}
			
			if (this.headers.containsKey(IN_REPLY_TO)) {
				this.inReplyTo = this.headers.get(IN_REPLY_TO).toString();
			}
			
			if (this.headers.containsKey(REFERENCES)) {
				this.references = this.headers.get(REFERENCES).toString();
			}
		}
		catch (Exception e) {
			
		}
	}
}
