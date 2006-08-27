/*
 * Created on 26/08/2006 22:20:46
 */
package net.jforum.api.integration.mail.pop;

import java.util.Iterator;

import net.jforum.JForumExecutionContext;
import net.jforum.SessionFacade;
import net.jforum.context.JForumContext;
import net.jforum.context.RequestContext;
import net.jforum.context.standard.StandardRequestContext;
import net.jforum.entities.User;
import net.jforum.entities.UserSession;
import net.jforum.util.preferences.ConfigKeys;

/**
 * @author Rafael Steil
 * @version $Id: POPPostAction.java,v 1.2 2006/08/27 01:50:21 rafaelsteil Exp $
 */
public class POPPostAction
{
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
				
				UserSession us = new UserSession();
				us.setUserId(user.getId());
				us.setUsername(us.getUsername());
				us.setSessionId(sessionId);
				
				SessionFacade.add(us, sessionId);
				SessionFacade.setAttribute(ConfigKeys.LOGGED, "1");
			}
		}
		finally {
			JForumExecutionContext.finish();
		}
	}
	
	private User findUser(String email)
	{
		return null;
		//return DataAccessDriver.getInstance().newUserDAO().findByEmail(email);
	}
}
