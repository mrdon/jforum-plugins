/*
 * Copyright (c) 2003, Rafael Steil
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms,
 * with or without modification, are permitted provided
 * that the following conditions are met:
 * 
 * 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor
 * the names of its contributors may be used to endorse
 * or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 *
 * This file creation date: 30/03/2003 / 02:37:20
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.generic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jforum.JForum;
import net.jforum.entities.Forum;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @author Vanessa Sabino
 * @version $Id: ForumModel.java,v 1.7 2004/11/05 03:29:45 rafaelsteil Exp $
 */
public class ForumModel extends AutoKeys implements net.jforum.model.ForumModel 
{
    /**
     * @see net.jforum.model.ForumModel#selectById(int)
     */
    public Forum selectById(int forumId) throws Exception {
        PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.selectById"));
        p.setInt(1, forumId);

        ResultSet rs = p.executeQuery();

        Forum f = new Forum();

        if (rs.next()) {
            f = this.fillForum(rs);
        }

        rs.close();
        p.close();

        return f;
    }
    
    protected Forum fillForum(ResultSet rs) throws Exception
    {
    	Forum f = new Forum();
    	
    	f.setId(rs.getInt("forum_id"));
        f.setIdCategories(rs.getInt("categories_id"));
        f.setName(rs.getString("forum_name"));
        f.setDescription(rs.getString("forum_desc"));
        f.setOrder(rs.getInt("forum_order"));
        f.setTotalTopics(rs.getInt("forum_topics"));
        f.setLastPostId(rs.getInt("forum_last_post_id"));
        f.setModerated(rs.getInt("moderated") > 0);
        f.setTotalPosts(rs.getInt("total_posts"));
        
        return f;
    }

    /**
     * @see net.jforum.model.ForumModel#selectAll()
     */
    public List selectAll() throws Exception {
        PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.selectAll"));
        List l = new ArrayList();

        ResultSet rs = p.executeQuery();

        while (rs.next()) {
            l.add(this.fillForum(rs));
        }

        rs.close();
        p.close();

        return l;
    }

    /**
     * @see net.jforum.model.ForumModel#setOrderUp(int)
     */
    public void setOrderUp(int forumId) throws Exception {
        int order = 0;
        int maxOrder = 0;

        // Retrieves the current value of of forum_order
        PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.getOrder"));
        p.setInt(1, forumId);

        ResultSet rs = p.executeQuery();

        if (rs.next()) {
            order = rs.getInt("forum_order");
        }

        rs.close();
        p.close();

        // Retireves the max value of forum_order
        p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.getMaxOrder"));
        rs = p.executeQuery();

        if (rs.next()) {
            maxOrder = rs.getInt("maxOrder");
        }

        rs.close();
        p.close();

        if (order < maxOrder) {
            // Sets the forum that is in the higher order to the current order
            p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.setOrderByOrder"));
            p.setInt(1, order);
            p.setInt(2, ++order);

            p.executeUpdate();

            p.close();

            // Sets the forum to the higher order
            p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.setOrderById"));
            p.setInt(1, order);
            p.setInt(2, forumId);

            p.executeUpdate();

            p.close();
        }
    }

    /**
     * @see net.jforum.model.ForumModel#setOrderDown(int)
     */
    public void setOrderDown(int forumId) throws Exception {
        int order = 0;

        // Retireves the current value of of forum_order
        PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.getOrder"));
        p.setInt(1, forumId);

        ResultSet rs = p.executeQuery();

        if (rs.next()) {
            order = rs.getInt("forum_order");
        }

        rs.close();
        p.close();

        if (order > 1) {
            // Sets the forum that is in the lower order to the current order
            p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.setOrderByOrder"));
            p.setInt(1, order);
            p.setInt(2, --order);

            p.executeUpdate();

            p.close();

            // Sets the forum to the lower order
            p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.setOrderById"));
            p.setInt(1, order);
            p.setInt(2, forumId);

            p.executeUpdate();

            p.close();
        }
    }

    /**
     * @see net.jforum.model.ForumModel#delete(int)
     */
    public void delete(int forumId) throws Exception {
        PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.delete"));
        p.setInt(1, forumId);

        p.executeUpdate();

        p.close();
    }

    /**
     * @see net.jforum.model.ForumModel#update(net.jforum.Forum)
     */
    public void update(Forum forum) throws Exception {
        PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.update"));

        p.setInt(1, forum.getCategoryId());
        p.setString(2, forum.getName());
        p.setString(3, forum.getDescription());
        p.setInt(4, forum.isModerated() ? 1 : 0);
        p.setInt(5, forum.getId());

        // Order, TotalTopics and LastPostId must be updated using the respective methods
        p.executeUpdate();
        p.close();
    }

    /**
     * @see net.jforum.model.ForumModel#addNew(net.jforum.Forum)
     */
    public int addNew(Forum forum) throws Exception {
        // Gets the higher order
        int maxOrder = 0;
        PreparedStatement pOrder = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.getMaxOrder"));
        ResultSet rs = pOrder.executeQuery();

        if (rs.next()) {
            maxOrder = rs.getInt("maxOrder");
        }

        rs.close();
        pOrder.close();

        // Updates the order
        PreparedStatement p = this.getStatementForAutoKeys("ForumModel.addNew");
        
        p.setInt(1, forum.getCategoryId());
        p.setString(2, forum.getName());
        p.setString(3, forum.getDescription());
        p.setInt(4, ++maxOrder);
        p.setInt(5, forum.isModerated() ? 1 : 0);

        int forumId = this.executeAutoKeysQuery(p);
        
        p.close();
        
        return forumId;
    }

    /**
     * @see net.jforum.model.ForumModel#setLastPost(int, int)
     */
    public void setLastPost(int forumId, int postId) throws Exception {
        PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.updateLastPost"));

        p.setInt(1, postId);
        p.setInt(2, forumId);

        p.executeUpdate();

        p.close();
    }

    /**
     * @see net.jforum.model.ForumModel#setTotalTopics(int)
     */
    public void incrementTotalTopics(int forumId, int count) throws Exception 
    {
        PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.incrementTotalTopics"));        
        p.setInt(1, count);
        p.setInt(2, forumId);
        p.executeUpdate();
        p.close();
    }
    
	/**
	 * @see net.jforum.model.ForumModel#setTotalTopics(int)
	 */
	public void decrementTotalTopics(int forumId, int count) throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.decrementTotalTopics"));
		p.setInt(1, count);
		p.setInt(2, forumId);
		p.executeUpdate();
		p.close();
		
		// If there are no more topics, when clean the
		// last post id information 
		int totalTopics = this.getTotalTopics(forumId);
		if (totalTopics < 1) {
			this.setLastPost(forumId, 0);
		}
	}

	/** 
	 * @see net.jforum.model.ForumModel#getLastPostInfo(int)
	 */
	public Map getLastPostInfo(int forumId) throws Exception 
	{
		Map m = new HashMap();
		
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.lastPostInfo"));
		p.setInt(1, forumId);

		ResultSet rs = p.executeQuery();
		
		if (rs.next()) {
			m.put("userName", rs.getString("username"));
			m.put("userId", new Integer(rs.getInt("user_id")));
			
			SimpleDateFormat df = new SimpleDateFormat(SystemGlobals.getValue(ConfigKeys.DATE_TIME_FORMAT));
			m.put("postTime", df.format(rs.getTimestamp("post_time")));
			m.put("postId", new Integer(rs.getInt("post_id")));
			m.put("topicId", new Integer(rs.getInt("topic_id")));
			m.put("postTimeMillis", new Long(rs.getTimestamp("post_time").getTime()));
			m.put("topic_replies", new Integer(rs.getInt("topic_replies")));
		}
		
		p.close();
		rs.close();
		
		return m;
	}

	/** 
	 * @see net.jforum.model.ForumModel#getTotalMessages()
	 */
	public int getTotalMessages() throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.totalMessages"));
		ResultSet rs = p.executeQuery();
		rs.next();
		
		int total = rs.getInt("total_messages");
		p.close();
		rs.close();
		
		return total;
	}

	/** 
	 * @see net.jforum.model.ForumModel#getTotalTopics(int)
	 */
	public int getTotalTopics(int forumId) throws Exception 
	{
		int total = 0;
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.getTotalTopics"));
		p.setInt(1, forumId);
		ResultSet rs = p.executeQuery();
		
		if (rs.next()) {
			total = rs.getInt(1);
		}
		
		p.close();
		rs.close();
		
		return total;
	}

	/** 
	 * @see net.jforum.model.ForumModel#getMaxPostId(int)
	 */
	public int getMaxPostId(int forumId) throws Exception 
	{
		int id = -1;
		
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.getMaxPostId"));
		p.setInt(1, forumId);
		
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			id = rs.getInt("post_id");
		}
		
		rs.close();
		p.close();
		
		return id;
	}

	/** 
	 * @see net.jforum.model.ForumModel#moveTopics(java.lang.String[], int, int)
	 */
	public void moveTopics(String[] topics, int fromForumId, int toForumId) throws Exception 
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(SystemGlobals.getSql("ForumModel.moveTopics"));
		PreparedStatement t = JForum.getConnection().prepareStatement(SystemGlobals.getSql("PostModel.setForumByTopic"));
		
		p.setInt(1, toForumId);
		t.setInt(1, toForumId);
		
		for (int i = 0; i < topics.length; i++) {
			int topicId = Integer.parseInt(topics[i]);
			p.setInt(2, topicId);
			t.setInt(2, topicId);
			
			p.executeUpdate();
			t.executeUpdate();
		}
		
		this.decrementTotalTopics(fromForumId, topics.length);
		this.incrementTotalTopics(toForumId, topics.length);
		
		p.close();
	}
}
