/*
 * Copyright (c) 2003, 2004 Rafael Steil
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
 * This file creation date: 14/01/2004 / 22:02:56
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.entities.Topic;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.SearchData;
import net.jforum.model.SearchModel;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.security.SecurityConstants;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: SearchAction.java,v 1.9 2004/11/15 12:05:34 rafaelsteil Exp $
 */
public class SearchAction extends Command 
{
	private String searchTerms;
	private String forum;
	private String category;
	private String sortBy;
	private String sortDir;
	private String kw;
	private String author;
	private String postTime;
	private String s;
	
	private static HashMap fieldsMap = new HashMap();
	
	static {
		fieldsMap.put("search_terms", "searchTerms");
		fieldsMap.put("search_forum", "forum");
		fieldsMap.put("search_cat", "catgory");
		fieldsMap.put("sort_by", "sortBy");
		fieldsMap.put("sort_dir", "sortDir");
		fieldsMap.put("search_keywords", "kw");
		fieldsMap.put("search_author", "author");
		fieldsMap.put("post_time", "postTime");
		fieldsMap.put("start", "s");
	}
	
	public void filters() throws Exception
	{
		JForum.getContext().put("moduleAction", "search.htm");
		JForum.getContext().put("categories", ForumRepository.getAllCategories());
	}
	
	private void getSearchFields()
	{
		this.searchTerms = this.addSlashes(JForum.getRequest().getParameter("search_terms"));
		this.forum = this.addSlashes(JForum.getRequest().getParameter("search_forum"));
		this.category = this.addSlashes(JForum.getRequest().getParameter("search_cat"));
		this.sortBy = this.addSlashes(JForum.getRequest().getParameter("sort_by"));
		this.sortDir = this.addSlashes(JForum.getRequest().getParameter("sort_dir"));
		this.kw = this.addSlashes(JForum.getRequest().getParameter("search_keywords"));
		this.author = this.addSlashes(JForum.getRequest().getParameter("search_author"));
		this.postTime = this.addSlashes(JForum.getRequest().getParameter("post_time"));
		this.s = JForum.getRequest().getParameter("start");
	}
	
	public void search() throws Exception
	{
		this.getSearchFields();
		
		SearchData sd = new SearchData();
		sd.setKeywords(kw);
		sd.setAuthor(author);
		sd.setOrderByField(sortBy);
		sd.setOrderBy(sortDir);
		if (postTime != null) {
			sd.setTime(new Date(Long.parseLong(postTime)));		    
		}
		
		if (searchTerms != null) {
			sd.setUseAllWords(searchTerms.equals("any") ? false : true);
		}
		
		if (forum != null && !forum.equals("")) {
			sd.setForumId(Integer.parseInt(forum));
		}
		
		if (category != null && !category.equals("")) {
			sd.setCategoryId(Integer.parseInt(category));
		}
		
		int start = 0;
		int recordsPerPage = SystemGlobals.getIntValue(ConfigKeys.TOPICS_PER_PAGE);
		
		if (s != null) {
			start = Integer.parseInt(s);
		}
		
		SearchModel sm = DataAccessDriver.getInstance().newSearchModel();

		// Clean the search
		if (JForum.getRequest().getParameter("clean") != null) {
			sm.cleanSearch();
		}
		else {
			sd.setSearchStarted(true);
		}
		
		List allTopics = this.onlyAllowedData(sm.search(sd));
		int totalTopics = allTopics.size();
		int sublistLimit = recordsPerPage + start > totalTopics ? totalTopics : recordsPerPage + start;
		
		List topics = TopicsCommon.prepareTopics(allTopics.subList(start, sublistLimit));
		
		JForum.getContext().put("fr", new ForumRepository());
		
		JForum.getContext().put("topics", topics);
		JForum.getContext().put("categories", ForumRepository.getAllCategories());
		JForum.getContext().put("moduleAction", "search_result.htm");
		
		// Pagination
		JForum.getContext().put("kw", kw);
		JForum.getContext().put("terms", searchTerms);
		JForum.getContext().put("forum", forum);
		JForum.getContext().put("category", category);
		JForum.getContext().put("orderField", sortBy);
		JForum.getContext().put("orderBy", sortDir);
		JForum.getContext().put("author", author);
		JForum.getContext().put("postTime", postTime);
		
		JForum.getContext().put("totalPages", new Double(Math.ceil((float)totalTopics / recordsPerPage)));
		JForum.getContext().put("recordsPerPage", new Integer(recordsPerPage));
		JForum.getContext().put("postsPerPage", new Integer(SystemGlobals.getIntValue(ConfigKeys.POST_PER_PAGE)));
		JForum.getContext().put("totalRecords", new Integer(totalTopics));
		JForum.getContext().put("thisPage", new Integer(start));
		
		String openModeration = JForum.getRequest().getParameter("openModeration");
		if (openModeration == null) {
			openModeration = "0";
		}
		
		JForum.getContext().put("openModeration", openModeration.equals("1"));
		TopicsCommon.topicListingBase();
	}
	
	private List onlyAllowedData(List topics)
	{
		List l = new ArrayList();
		
		for (Iterator iter = topics.iterator(); iter.hasNext(); ) {
			Topic t = (Topic)iter.next();
			if (SecurityRepository.canAccess(SecurityConstants.PERM_FORUM, Integer.toString(t.getForumId()))) {
				l.add(t);
			}
		}
		
		return l;
	}
	
	public void doModeration() throws Exception
	{
		new ModerationHelper().doModeration(this.makeRedirect());
	}
	
	public void moveTopic() throws Exception
	{
		new ModerationHelper().moveTopicsSave(this.makeRedirect());
	}
	
	public void moderationDone() throws Exception
	{
		new ModerationHelper().moderationDone(this.makeRedirect());
	}
	
	private String makeRedirect() throws Exception
	{
		String persistData = JForum.getRequest().getParameter("persistData");
		if (persistData == null) {
			this.getSearchFields();
		}
		else {
			String[] p = persistData.split("&");
			
			for (int i = 0; i < p.length; i++) {
				String[] v = p[i].split("=");
				
				String name = (String)fieldsMap.get(v[0]);
				if (name != null) {
					Field field = this.getClass().getDeclaredField(name);
					if (field != null && v[1] != null && !v[1].equals("")) {
						field.set(this, v[1]);
					}
				}
			}
		}

		StringBuffer path = new StringBuffer(512);
		path.append(JForum.getRequest().getContextPath()).append("/jforum" 
				+ SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION)
				+ "?module=search&action=search");
		
		if (this.forum != null) { 
			path.append("&search_forum=").append(this.forum); 
		}
		
		if (this.searchTerms != null) { 
			path.append("&search_terms=").append(this.searchTerms); 
		}
		
		if (this.category != null) {
			path.append("&search_cat=").append(this.category);
		}
		
		if (this.sortDir != null) {
			path.append("&sort_dir=").append(this.sortDir);
		}
		
		if (this.sortBy != null) {
			path.append("&sort_by=").append(this.sortBy);
		}
		
		if (this.kw != null) {
			path.append("&search_keywords=").append(this.kw);
		}
		
		if (this.postTime != null) {
			path.append("&post_time=").append(this.postTime);
		}
		
		if (this.s != null) {
			path.append("&start=").append(this.s);
		}
		
		return path.toString();
	}
	
	private String addSlashes(String s)
	{
		if (s != null) {
			s = s.replaceAll("'", "\\'");
			s = s.replaceAll("\"", "\\\"");
		}
		
		return s;
	}
	
	/** 
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception 
	{
		this.filters();
	}
}
