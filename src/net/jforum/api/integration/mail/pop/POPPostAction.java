/*
 * Created on 26/08/2006 22:20:46
 */
package net.jforum.api.integration.mail.pop;

import java.util.Iterator;

import org.apache.log4j.Logger;

import net.jforum.JForumExecutionContext;
import net.jforum.SessionFacade;
import net.jforum.context.JForumContext;
import net.jforum.context.RequestContext;
import net.jforum.context.standard.StandardRequestContext;
import net.jforum.dao.DataAccessDriver;
import net.jforum.dao.ForumDAO;
import net.jforum.entities.Topic;
import net.jforum.entities.User;
import net.jforum.entities.UserSession;
import net.jforum.util.preferences.ConfigKeys;

/**
 * @author Rafael Steil
 * @version $Id: POPPostAction.java,v 1.3 2006/08/28 23:22:28 rafaelsteil Exp $
 */
public class POPPostAction
{
	private static Logger logger = Logger.getLogger(POPPostAction.class);
	
	public void insertMessages(POPParser parser)
	{
		long ms = System.currentTimeMillis();
		int counter = 0;
		
		try {
			JForumExecutionContext ex = JForumExecutionContext.get();
			
			RequestContext request = new StandardRequestContext();
			ex.setForumContext(new JForumContext("/", "", request, null));
			
			JForumExecutionContext.set(ex);
			
			for (Iterator iter = parser.getMessages().iterator(); iter.hasNext(); ) {
				POPMessage m = (POPMessage)iter.next();
				String sessionId = ms + m.getSender() + counter++;
				
				User user = this.findUser(m.getSender());
				
				if (user == null) {
					// TODO: now what?
					logger.warn("Could not find user. Email is " + m.getSender());
					continue;
				}
				
				try {
					UserSession us = new UserSession();
					us.setUserId(user.getId());
					us.setUsername(us.getUsername());
					us.setSessionId(sessionId);
					
					SessionFacade.add(us, sessionId);
					SessionFacade.setAttribute(ConfigKeys.LOGGED, "1");
					
					SessionFacade.removeAttribute(ConfigKeys.LAST_POST_TIME);
					SessionFacade.setAttribute(ConfigKeys.REQUEST_IGNORE_CAPTCHA, "1");
					
					this.insertMessage(m, user);
				}
				finally {
					SessionFacade.remove(sessionId);
				}
			}
		}
		finally {
			JForumExecutionContext.finish();
		}
	}
	
	private void insertMessage(POPMessage m, User user)
	{
		this.addDataToRequest(m, user);
	}
	
	private void addDataToRequest(POPMessage m, User user)
	{
		RequestContext request = JForumExecutionContext.getRequest();
		request.addParameter("forumId", Integer.toString(this.discoverForumId(m.getListEmail())));
		request.addParameter("topic_type", Integer.toString(Topic.TYPE_NORMAL));
		request.addParameter("quick", "1");
		request.addParameter("subject", m.getSubject());
		request.addParameter("message", m.getMessage());
		
		if (!user.isBbCodeEnabled()) {
			request.addParameter("disable_bbcode", "on");
		}
		
		if (!user.isSmiliesEnabled()) {
			request.addParameter("disable_smilies", "on");
		}
		
		if (!user.isHtmlEnabled()) {
			request.addParameter("disable_html", "on");
		}
		
	}
	
	private int discoverForumId(String listEmail)
	{
		ForumDAO dao = DataAccessDriver.getInstance().newForumDAO();
		return dao.discoverForumId(listEmail);
	}
	
	private User findUser(String email)
	{
		return DataAccessDriver.getInstance().newUserDAO().findByEmail(email);
	}
}
