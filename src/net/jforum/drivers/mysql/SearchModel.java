/*
 * Copyright (c) 2003, Rafael Steil
 * All rights reserved.

 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:

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
 * This file creation date: 25/02/2004 - 19:32:42
 * net.jforum.drivers.mysql.SearchModel.java
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import net.jforum.JForum;
import net.jforum.entities.Post;
import net.jforum.model.SearchData;
import net.jforum.util.SystemGlobals;

/**
 * @author Rafael Steil
 */
public class SearchModel implements net.jforum.model.SearchModel 
{
	/* 
	 * @see net.jforum.model.SearchModel#search(net.jforum.model.SearchData)
	 */
	public ArrayList search(SearchData sd) throws Exception 
	{
		ArrayList l = new ArrayList();
		ArrayList topics = new ArrayList();
		
		if (sd.getTime() == null) {
			topics = this.topicsByKeyword(sd);
		}
		else {
			topics = this.topicsByTime(sd);
		}
		
		StringBuffer criterias = new StringBuffer(256);
		if (sd.getForumId() != 0) {
			criterias.append(" AND t.forum_id = "+ sd.getForumId());
		}
		
		if (sd.getCategoryId() != 0) {
			criterias.append(" AND f.categories_id = "+ sd.getCategoryId());
		}
		
		if (sd.getOrderByField() == null || sd.getOrderByField().equals("")) {
			sd.setOrderByField("p.post_time");
		}
		
		StringBuffer ids = new StringBuffer(128);
		for (Iterator iter = topics.iterator(); iter.hasNext(); ) {
			ids.append(iter.next()).append(",");
		}
		
		ids.delete(ids.length() - 1, ids.length());
		
		String sql = SystemGlobals.getSql("SearchModel.searchBase");
		// Prepare the query
		sql = sql.replaceAll(":orderByField:", sd.getOrderByField());
		sql = sql.replaceAll(":orderBy:", sd.getOrderBy());
		sql = sql.replaceAll(":criterias:", criterias.toString());
		sql = sql.replaceAll(":topics:", ids.toString());
		
		PreparedStatement p = JForum.getConnection().prepareStatement(sql);
		ResultSet rs = p.executeQuery();
		
		l = new TopicModel().fillTopicsData(rs);
		
		rs.close();
		p.close();
		
		return l;
	}
	
	private ArrayList topicsByTime(SearchData sd) throws Exception
	{
		PreparedStatement search = JForum.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.searchByTime"));
		search.setString(1, sd.getTime());

		ArrayList topics = new ArrayList();
		ResultSet rs = search.executeQuery();
		while (rs.next()) {
			topics.add(new Integer(rs.getInt("topic_id")));
		}
		
		return topics;
	}
	
	private ArrayList topicsByKeyword(SearchData sd) throws Exception
	{
		PreparedStatement searchWord = JForum.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.searchByWord"));

		HashMap eachWordMap = new HashMap();

		// posts ids
		for (int i = 0; i < sd.getKeywords().length; i++) {
			searchWord.setString(1, sd.getKeywords()[i]);
			
			HashSet postsIds = new HashSet();
			ResultSet rs = searchWord.executeQuery();
			while (rs.next()) {
				postsIds.add(new Integer(rs.getInt("post_id")));
			}
			
			if (postsIds.size() > 0) {
				eachWordMap.put(sd.getKeywords()[i], postsIds);
			}
		}
		
		String sql = SystemGlobals.getSql("SearchModel.selectTopicsIds");
		StringBuffer sb = new StringBuffer(512);
		for (Iterator iter = eachWordMap.values().iterator(); iter.hasNext(); ) {
			sb.append(iter.next()).append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		
		sql = sql.replaceAll(":topics:", sb.toString());
		searchWord = JForum.getConnection().prepareStatement(sql);
		
		// topics ids
		eachWordMap = new HashMap();

		searchWord = JForum.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.searchByWord"));
		for (int i = 0; i < sd.getKeywords().length; i++) {
			searchWord.setString(1, sd.getKeywords()[i]);
			
			HashSet postsIds = new HashSet();
			ResultSet rs = searchWord.executeQuery();
			while (rs.next()) {
				postsIds.add(new Integer(rs.getInt("post_id")));
			}
			
			if (postsIds.size() > 0) {
				eachWordMap.put(sd.getKeywords()[i], postsIds);
			}
		}
		
		HashSet topicsToSearch = new HashSet();
		for (Iterator iter = eachWordMap.values().iterator(); iter.hasNext(); ) {
			HashSet s = (HashSet)iter.next();
			
			if (topicsToSearch.size() == 0) {
				topicsToSearch.addAll(s);
				continue;
			}
			
			if (sd.getUseAllWords()) {
				topicsToSearch.retainAll(s);
			}
			else {
				topicsToSearch.addAll(s);
			}
		}
		
		return new ArrayList(topicsToSearch);
	}
	
	public void insertSearchWords(Post post, String[] words) throws Exception
	{
		PreparedStatement insert = JForum.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.insertWords"),
						Statement.RETURN_GENERATED_KEYS);

		PreparedStatement existing = JForum.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.searchExistingWord"));

		PreparedStatement wordToPost = JForum.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.associateWordToPost"));
		wordToPost.setInt(1, post.getId());
		
		for (int i = 0; i < words.length; i++) {
			if (words[i].length() < 3) {
				continue;
			}
			
			// Verify if the current word is not in the database before proceeding
			int hash = words[i].hashCode();
			existing.setInt(1, hash);
			ResultSet rs = existing.executeQuery();
			
			if (!rs.next()) {
				insert.setInt(1, hash);
				insert.setString(2, words[i]);
				insert.executeUpdate();
				
				ResultSet idRs = insert.getGeneratedKeys();
				idRs.next();
				
				this.associateWordToPost(wordToPost, words[i], idRs.getInt(1), post);
			}
			else {
				int postId = rs.getInt("post_id");
				if (postId == 0) {
					insert.setInt(1, post.getId());
					insert.setInt(2, hash);
					insert.executeUpdate();
				}
			}
		}
	}
	
	private void associateWordToPost(PreparedStatement p, String word, int hash, Post post) throws Exception
	{
		p.setInt(2, hash);
		
		String subject = post.getSubject();
		int inSubject = 0;
		if (subject != null && !subject.equals("")) {
			inSubject = subject.indexOf(word) > -1 ? 1 : 0;
		}
		
		p.setInt(3, inSubject);
		p.executeUpdate();
	}

}
