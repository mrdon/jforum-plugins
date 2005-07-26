/*
 *	Class created on Jul 15, 2005
 */ 
package net.jforum.summary;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.jforum.dao.DataAccessDriver;
import net.jforum.dao.SummaryDAO;
import net.jforum.util.mail.Spammer;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.Logger;

import freemarker.template.SimpleHash;

/**
 * Manage the Summary sents.
 * 
 * @see net.jforum.summary.SummaryJob
 * @see net.jforum.summary.SummaryScheduler
 * 
 * @author Franklin S. Dattein (<a href="mailto:franklin@hp.com">franklin@hp.com</a>)
 *
 */
public class SummaryModel extends Spammer{
    private SummaryDAO dao = null;
    private static Logger logger = Logger.getLogger(SummaryModel.class);
    
    public SummaryModel(){
        dao = DataAccessDriver.getInstance().newSummaryDAO();        
    }
    
    public void sendPostsSummary(List recipients) throws Exception{
        logger.info("Sending Weekly summary...");                
       
        //Gets a Date seven days before now
        int daysBefore = Integer.parseInt(SystemGlobals.getValue(ConfigKeys.SUMMARY_DAYS_BEFORE));  
        
        //New date "X" days before now, where "X" is the number set on the variable daysBefore
        long dateBefore = Calendar.getInstance().getTimeInMillis() - (daysBefore * 1000 * 60 * 60 * 24);
                              
        List posts = listPosts(new Date(dateBefore),new Date());        
        
        String forumLink = SystemGlobals.getValue(ConfigKeys.FORUM_LINK);
        if (!forumLink.endsWith("/")) {
            forumLink += "/";
        }

        //String url = forumLink + "/" + SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION);
        SimpleHash params = new SimpleHash();
        params.put("posts", posts);
        params.put("url", forumLink);        
               
        String subject = SystemGlobals.getValue(ConfigKeys.MAIL_SUMMARY_SUBJECT);
        if(subject == null)
            subject = "Weekly summary";
        
        //FIXME:use real recipients
//        recipients = null;
//        recipients = new ArrayList();
//        recipients.add("franklin@hp.com");
        
        this.prepareMessage(recipients, params, subject,  SystemGlobals.getValue(ConfigKeys.MAIL_SUMMARY_FILE));
        super.dispatchMessages();
    }    


    /**
     * List all recipients able to receive posts summaries.
     * @return List of users
     * @throws Exception 
     */
    public List listRecipients() throws Exception{
        return dao.listRecipients();        
    }
    
    /**
     * List last posts of a period like a week, day or month.
     * 
     * @param firstDate First date of a period.
     * @param lastDate Last date of a period.
     * @return List of Posts
     * @throws Exception 
     * 
     */
    public List listPosts(Date firstDate, Date lastDate) throws Exception{    
        return dao.selectLastPosts(firstDate, lastDate);        
    }

}
