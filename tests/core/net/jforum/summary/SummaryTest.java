/*
 *	Class created on Jul 15, 2005
 */ 
package net.jforum.summary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import net.jforum.TestCaseUtils;
import net.jforum.entities.Post;
import net.jforum.entities.User;

import org.quartz.SchedulerException;

/**
 * Test case for SummaryScheduler.
 * 
 * @see net.jforum.summary.SummaryScheduler
 * 
 * @author Franklin S. Dattein (<a href="mailto:franklin@hp.com">franklin@hp.com</a>)
 *
 */
public class SummaryTest extends TestCase {
    
    /**
     * Tests only the scheduler and your frquency.
     * @throws Exception 
     *
     */
    public void testScheduler() throws Exception{
        this.init();               
        try {
            SummaryScheduler.startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }        
    }
    
    public void testLoadRecipients() throws Exception{  
        this.init();
        TestCaseUtils.loadEnvironment();
        TestCaseUtils.initDatabaseImplementation();
        
        SummaryModel model = new SummaryModel();
        Iterator iter = model.listRecipients().iterator();
        while (iter.hasNext()) {
            User user = (User) iter.next();
            System.out.println(user.getUsername());
            
        }
        assertTrue(model.listRecipients().size()>0);       
    }
    
    public void testSendMails() throws Exception{
        this.init();
        
        SummaryModel model = new SummaryModel();
        //List recipients = model.listRecipients()// Do not use this at least you want to send e-mails to all users.
        List recipients = new ArrayList(1);
        recipients.add("fulano@detal.com");
        
        model.sendPostsSummary(recipients);
       
    }
    
    public void testListPosts() throws Exception{
        SummaryModel model= new SummaryModel();
//      Gets a Date seven days before now
        long weekBefore = Calendar.getInstance().getTimeInMillis() - (7 * 1000 * 60 * 60 * 24);
        Date firstDate = new Date(weekBefore);
        System.out.println(firstDate);
        Collection posts = model.listPosts(firstDate,new Date());
        Iterator iter = posts.iterator();
        while (iter.hasNext()) {
            Post post = (Post) iter.next();
            System.out.println(post.getSubject());
            
        }
        assertTrue(posts.size()>0);
        
    }
    private void init() throws Exception{
        TestCaseUtils.loadEnvironment();
        TestCaseUtils.initDatabaseImplementation();
    }

}
