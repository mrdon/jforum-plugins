/*
 * Created on 21/08/2006 22:00:12
 */
package net.jforum.api.integration.mail.pop;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

/**
 * @author Rafael Steil
 * @version $Id: POPParser.java,v 1.2 2006/08/27 01:50:22 rafaelsteil Exp $
 */
public class POPParser
{
	private List messages = new ArrayList();
	
	public void parseMessages(POPConnector connector)
	{
		Message[] messages = connector.listMessages();
		
		for (int i = 0; i < messages.length; i++) {
			this.messages.add(new POPMessage(messages[i]));
		}
	}
	
	public List getMessages()
	{
		return this.messages;
	}
}
