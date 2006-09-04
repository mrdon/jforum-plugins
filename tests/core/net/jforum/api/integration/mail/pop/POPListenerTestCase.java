/*
 * Created on 28/08/2006 22:58:20
 */
package net.jforum.api.integration.mail.pop;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;
import net.jforum.ConfigLoader;
import net.jforum.ForumStartup;
import net.jforum.TestCaseUtils;
import net.jforum.repository.RankingRepository;
import net.jforum.repository.SmiliesRepository;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: POPListenerTestCase.java,v 1.3 2006/09/04 01:00:07 rafaelsteil Exp $
 */
public class POPListenerTestCase extends TestCase
{
	public void testAll() throws Exception
	{
		TestCaseUtils.loadEnvironment();
		TestCaseUtils.initDatabaseImplementation();
		ConfigLoader.startCacheEngine();
		
		ForumStartup.startForumRepository();
		RankingRepository.loadRanks();
		SmiliesRepository.loadSmilies();
		
		SystemGlobals.setValue(ConfigKeys.SEARCH_INDEXING_ENABLED, "false");
		
		POPListener listener = new POPListenerMock();
		((POPConnectorMock)listener.getConnector()).setMessages(this.createMessages());
		listener.execute(null);
	}
	
	private Message[] createMessages() throws Exception
	{
		return new Message[] {
				this.newMessageMock("ze@zinho.com", "Mail Message 1", "forum_test@jforum.testcase", "Mail message contents 1")
		};
	}
	
	private MessageMock newMessageMock(String sender, String subject, String listEmail, 
			String text) throws Exception
	{
		MessageMock m = new MessageMock(null, new ByteArrayInputStream(text.getBytes()));
		
		m.setFrom(new InternetAddress(sender));
		m.setRecipient(RecipientType.TO, new InternetAddress(listEmail));
		m.setSubject(subject);
		
		return m;
	}
	
	private static class MessageMock extends MimeMessage
	{
		private InputStream is;
		
		public MessageMock(Session session, InputStream is) throws MessagingException
		{
			super(session, is);
			this.is = is;
		}
		
		public InputStream getInputStream() throws IOException, MessagingException
		{
			return this.is;
		}
		
		public String getContentType() throws MessagingException
		{
			return "text/plain";
		}
	}
}
