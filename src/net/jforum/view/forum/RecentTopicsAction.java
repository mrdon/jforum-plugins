/*
 * Created on Oct 19, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.jforum.view.forum;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import net.jforum.Command;
import net.jforum.entities.Topic;
import net.jforum.entities.Forum;
import net.jforum.JForum;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.TopicRepository;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * Display a list of recent Topics
 *
 */
public class RecentTopicsAction extends Command{
	public void list() throws Exception
	{
		int postsPerPage = SystemGlobals.getIntValue(ConfigKeys.POST_PER_PAGE);
		List tmpTopics = TopicRepository.getRecentTopics();
		
		List forums = new ArrayList(postsPerPage);
		Iterator iter = tmpTopics.iterator();
		while (iter.hasNext()) 
		{
			Topic t = (Topic)iter.next();
			// Get name of forum that the topic refers to
			Forum f = ForumRepository.getForum(t.getForumId());
			forums.add(f);
		}
		
		JForum.getContext().put("postsPerPage", new Integer(postsPerPage));
		JForum.getContext().put("topics", TopicsCommon.prepareTopics(tmpTopics));
		JForum.getContext().put("forums", forums);
		JForum.getContext().put("moduleAction", "recent_thread.htm");
		
		TopicsCommon.topicListingBase();
	}
}
