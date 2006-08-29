/*
 * Created on 28/08/2006 22:58:20
 */
package net.jforum.api.integration.mail.pop;

import net.jforum.ConfigLoader;
import net.jforum.ForumStartup;
import net.jforum.TestCaseUtils;
import net.jforum.repository.RankingRepository;
import net.jforum.repository.SmiliesRepository;
import junit.framework.TestCase;

/**
 * @author Rafael Steil
 * @version $Id: POPListenerTestCase.java,v 1.1 2006/08/29 02:32:36 rafaelsteil Exp $
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
		
		POPListener listener = new POPListener();
		listener.execute(null);
	}
}
