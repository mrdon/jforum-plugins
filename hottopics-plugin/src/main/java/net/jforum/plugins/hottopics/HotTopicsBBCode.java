package net.jforum.plugins.hottopics;

import net.jforum.util.bbcode.BBCode;
import net.jforum.dao.TopicDAO;
import net.jforum.entities.Topic;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: mrdon
 * Date: Jun 3, 2009
 * Time: 7:38:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotTopicsBBCode extends BBCode
{
    private final TopicDAO topicDao;

    public HotTopicsBBCode(TopicDAO topicDao)
    {
        this.topicDao = topicDao;
        setTagName("hot-topics");
        setRegex("(?s)\\[hot-topics\\]");

    }

    @Override
    public String getReplace()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for (Topic topic : new ArrayList<Topic>(topicDao.selectHottestTopics(3)))
        {
            sb.append("<li>");
            sb.append(topic.getTitle());
            sb.append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }


}
