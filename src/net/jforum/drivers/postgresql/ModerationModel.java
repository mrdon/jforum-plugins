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
 * Created on Feb 13, 2005 11:32:30 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.jforum.JForum;
import net.jforum.entities.ModerationPendingInfo;
import net.jforum.entities.Post;
import net.jforum.entities.TopicModerationInfo;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Andowson Chang
 * @version $Id: ModerationModel.java,v 1.1 2005/02/13 17:06:42 andowson Exp $
 */
public class ModerationModel extends net.jforum.drivers.generic.ModerationModel
{	
	/**
	 * @see net.jforum.model.ModerationModel#topicsByForum(int, int, int)
	 */
	public List topicsByForum(int forumId, int start, int count) throws Exception
	{
		List l = new ArrayList();
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("ModerationModel.topicsByForum"));
		p.setInt(1, forumId);
		p.setInt(2, count);
		p.setInt(3, start);
				
		int lastId = 0;
		TopicModerationInfo info = null;
		
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("topic_id");
			if (id != lastId) {
				lastId = id;
				
				if (info != null) {
					l.add(info);
				}
				
				info = new TopicModerationInfo();
				info.setTopicId(id);
				info.setTopicTitle(rs.getString("topic_title"));
			}
			
			info.addPost(this.getPost(rs));
		}
		
		if (info != null) {
			l.add(info);
		}
		
		rs.close();
		p.close();
		
		return l;
	}	
}
