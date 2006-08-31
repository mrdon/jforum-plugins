/*
 * Created on 21/08/2006 21:07:36
 */
package net.jforum.api.integration.mail.pop;

import java.util.Iterator;
import java.util.List;

import net.jforum.dao.DataAccessDriver;
import net.jforum.entities.MailIntegration;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Rafael Steil
 * @version $Id: POPListener.java,v 1.4 2006/08/31 02:12:24 rafaelsteil Exp $
 */
public class POPListener implements Job
{
	protected POPConnector connector = new POPConnector();
	
	public void execute(JobExecutionContext jobContext) throws JobExecutionException
	{
		List integrationList = DataAccessDriver.getInstance().newMailIntegrationDAO().findAll();
		POPParser parser = new POPParser();
		
		for (Iterator iter = integrationList.iterator(); iter.hasNext(); ) {
			MailIntegration integration = (MailIntegration)iter.next();
			
			connector.setMailIntegration(integration);
			
			try {
				connector.openConnection();
				parser.parseMessages(connector);
				
				POPPostAction postAction = new POPPostAction();
				postAction.insertMessages(parser);
			}
			finally {
				connector.closeConnection(false);
			}
			
			POPPostAction action = new POPPostAction();
			action.insertMessages(parser);
		}
	}
}
