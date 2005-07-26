/*
 *	Class created on Jul 15, 2005
 */ 
package net.jforum.summary;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SummaryJob implements Job{   
    
    private static Logger logger = Logger.getLogger(SummaryJob.class);
           
    /**
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    public void execute(JobExecutionContext context) throws JobExecutionException{
        SummaryModel model = new SummaryModel();
        try {            
            model.sendPostsSummary(model.listRecipients());
        } catch (Exception e) {
            logger.warn(e);
        }           
    }       
}
