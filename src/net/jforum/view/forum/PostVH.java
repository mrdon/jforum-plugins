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
 * This file creation date: May 3, 2003 / 5:05:18 PM
 * net.jforum.view.forum.PostVH.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: PostVH.java,v 1.8 2004/04/24 22:11:32 rafaelsteil Exp $
 */
package net.jforum.view.forum;

import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jforum.Command;
import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.Post;
import net.jforum.entities.Smilie;
import net.jforum.entities.Topic;
import net.jforum.entities.User;
import net.jforum.entities.UserSession;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.model.PostModel;
import net.jforum.model.TopicModel;
import net.jforum.model.UserModel;
import net.jforum.repository.BBCodeRepository;
import net.jforum.repository.ForumRepository;
import net.jforum.repository.RankingRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.repository.SmiliesRepository;
import net.jforum.repository.TopicRepository;
import net.jforum.security.PermissionControl;
import net.jforum.security.SecurityConstants;
import net.jforum.util.bbcode.BBCode;
import net.jforum.util.concurrent.executor.QueuedExecutor;
import net.jforum.util.I18n;
import net.jforum.util.SystemGlobals;
import net.jforum.util.mail.EmailSenderTask;
import net.jforum.util.mail.TopicSpammer;
import net.jforum.util.rss.RSSTask;
import net.jforum.util.rss.TopicRSS;

/**
 * @author Rafael Steil
 */
public class PostVH extends Command 
{
	public void list() throws Exception
	{
		PostModel pm = DataAccessDriver.getInstance().newPostModel();
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		
		int postId = Integer.parseInt(JForum.getRequest().getParameter("post_id"));
		
		Topic topic = tm.selectById(Integer.parseInt(JForum.getRequest().getParameter("post_id")));
		
		// Shall we proceed?
		if (!this.shallProceed(topic.getForumId())) {
			return;
		}
		
		// The topic exists?
		if (topic.getId() == 0) {
			this.topicNotFound();
			return;
		}
		
		tm.incrementTotalViews(topic.getId());
		
		((HashMap)SessionFacade.getAttribute("topics_tracking")).put(new Integer(topic.getId()), new Long(topic.getLastPostTimeInMillis()));
		
		int start = 0;
		if (JForum.getRequest().getParameter("start") != null) {
			start = Integer.parseInt(JForum.getRequest().getParameter("start"));
		}
		
		int count = Integer.parseInt(SystemGlobals.getValue("postsPerPage").toString());
		
		ArrayList posts = pm.selectAllByTopicByLimit(postId, start, count);
		ArrayList helperList = new ArrayList();

		int userId = SessionFacade.getUserSession().getUserId();
		int anonymousUser = Integer.parseInt(SystemGlobals.getValue("anonymousUserId").toString());
		PermissionControl pc = SecurityRepository.get(userId);
		
		boolean canEdit = false;
		if (pc.canAccess(SecurityConstants.PERM_MODERATION_POST_EDIT)) {
			canEdit = true;
		}

		Iterator iter = posts.iterator();
		while (iter.hasNext()) {
			Post p = (Post)iter.next();
			if (canEdit || (p.getUserId() != anonymousUser && p.getUserId() == userId)) {
				p.setCanEdit(true);
			}
			
			helperList.add(this.preparePostText(p));
		}
		
		// Set the topic status as read
		tm.updateReadStatus(topic.getId(), userId, true);
		
		JForum.getContext().put("canRemove", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_POST_REMOVE));
		JForum.getContext().put("canEdit", SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_POST_EDIT));
		JForum.getContext().put("moduleAction", "post_show.htm");
		JForum.getContext().put("topic", topic);
		JForum.getContext().put("rank", new RankingRepository());
		JForum.getContext().put("posts",  helperList);
		JForum.getContext().put("forum", ForumRepository.getForum(topic.getForumId()));
		JForum.getContext().put("um", um);
		JForum.getContext().put("postId", new Integer(postId));
		
		// Topic Status
		JForum.getContext().put("STATUS_LOCKED", new Integer(Topic.STATUS_LOCKED));
		JForum.getContext().put("STATUS_UNLOCKED", new Integer(Topic.STATUS_UNLOCKED));
		
		// Pagination
		int totalPosts = tm.getTotalPosts(topic.getId());
		JForum.getContext().put("totalPages", new Double(Math.floor(totalPosts / count)));
		JForum.getContext().put("recordsPerPage", new Integer(count));
		JForum.getContext().put("totalRecords", new Integer(totalPosts));
		JForum.getContext().put("thisPage", new Integer(start));
	}
	
	private boolean shallProceed(int forumId)
	{
		if (!SecurityRepository.canAccess(SecurityConstants.PERM_FORUM, Integer.toString(forumId))) {
			new ModerationHelper().denied(I18n.getMessage("PostShow.denied"));
			return false;
		}
		
		return true;
	}
	
	private void topicNotFound()
	{
		JForum.getContext().put("moduleAction", "message.htm");
		JForum.getContext().put("message", I18n.getMessage("PostShow.TopicNotFound"));
	}
	
	private void postNotFound()
	{
		JForum.getContext().put("moduleAction", "message.htm");
		JForum.getContext().put("message", I18n.getMessage("PostShow.PostNotFound"));
	}
	
	public void insert() throws Exception
	{
		int forumId = Integer.parseInt(JForum.getRequest().getParameter("forum_id"));
		
		if (!this.anonymousPost(forumId)) {
			return;
		}
		
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		
		if (JForum.getRequest().getParameter("topic_id") != null) {
			int topicId = Integer.parseInt(JForum.getRequest().getParameter("topic_id"));
			Topic t = DataAccessDriver.getInstance().newTopicModel().selectById(topicId);
			
			if (t.getStatus() == Topic.STATUS_LOCKED) {
				this.topicLocked();
				return;
			}
			
			JForum.getContext().put("topicId", JForum.getRequest().getParameter("topic_id"));
			JForum.getContext().put("setType", false);
		}
		else {
			JForum.getContext().put("setType", true);
		}
		
		JForum.getContext().put("forum",  ForumRepository.getForum(forumId));		
		JForum.getContext().put("action", "insertSave");		
		JForum.getContext().put("moduleAction", "post_form.htm");
		JForum.getContext().put("isNewPost", true);
		
		int userId = SessionFacade.getUserSession().getUserId();
		JForum.getContext().put("user", DataAccessDriver.getInstance().newUserModel().selectById(userId));
	}
	
	public void edit() throws Exception
	{
		this.edit(false, null);
	}
	
	private void edit(boolean preview, Post p) throws Exception
	{
		int sUserId = SessionFacade.getUserSession().getUserId();
		int aId = Integer.parseInt(SystemGlobals.getValue("anonymousUserId").toString());
		boolean canAccess = false;
		
		if (!preview) { 
			PostModel pm = DataAccessDriver.getInstance().newPostModel();
			p = pm.selectById(Integer.parseInt(JForum.getRequest().getParameter("post_id")));

			// The post exist?
			if (p.getId() == 0) {
				this.postNotFound();
				return;
			}
		}
		
		boolean isModerator = SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_POST_EDIT);
		canAccess = (isModerator || p.getUserId() == sUserId);
		
		if ((sUserId != aId) && canAccess) {
			Topic topic = DataAccessDriver.getInstance().newTopicModel().selectById(p.getTopicId());
			
			if (!this.shallProceed(topic.getForumId())) {
				return;
			}
			
			if (topic.getStatus() == Topic.STATUS_LOCKED && !isModerator) {
				this.topicLocked();
				return;
			}
			
			if (preview && JForum.getRequest().getParameter("topic_type") != null) {
				topic.setType(Integer.parseInt(JForum.getRequest().getParameter("topic_type")));
			}

			JForum.getContext().put("forum",  ForumRepository.getForum(p.getForumId()));		
			JForum.getContext().put("action", "editSave");		
			
			JForum.getContext().put("post", p);
			JForum.getContext().put("setType", p.getId() == topic.getFirstPostId());
			JForum.getContext().put("topic", topic);
			JForum.getContext().put("moduleAction", "post_form.htm");
		}
		else {
			JForum.getContext().put("moduleAction", "message.htm");
			JForum.getContext().put("message", I18n.getMessage("CannotEditPost"));
		}
		
		JForum.getContext().put("user", DataAccessDriver.getInstance().newUserModel().selectById(sUserId));
	}
	
	public void quote() throws Exception
	{
		PostModel pm = DataAccessDriver.getInstance().newPostModel();
		Post p = pm.selectById(Integer.parseInt(JForum.getRequest().getParameter("post_id")));
		
		if (!this.anonymousPost(p.getForumId())) {
			return;
		}
		
		Topic t = DataAccessDriver.getInstance().newTopicModel().selectById(p.getTopicId());
		
		if (!this.shallProceed(t.getForumId())) {
			return;
		}
		
		if (t.getStatus() == Topic.STATUS_LOCKED) {
			this.topicLocked();
			return;
		}
		
		if (p.getId() == 0) {
			this.postNotFound();
			return;
		}
	
		JForum.getContext().put("forum",  ForumRepository.getForum(p.getForumId()));		
		JForum.getContext().put("action", "insertSave");		
		JForum.getContext().put("post", p);
		
		UserModel um = DataAccessDriver.getInstance().newUserModel();
		User u = um.selectById(p.getUserId());
		
		JForum.getContext().put("topicId", Integer.toString(p.getTopicId()));
		JForum.getContext().put("topic", DataAccessDriver.getInstance().newTopicModel().selectById(p.getTopicId()));
		JForum.getContext().put("quote", "true");		
		JForum.getContext().put("quoteUser", u.getUsername());
		JForum.getContext().put("moduleAction", "post_form.htm");
		JForum.getContext().put("setType", false);
		
		int userId = SessionFacade.getUserSession().getUserId();
		JForum.getContext().put("user", DataAccessDriver.getInstance().newUserModel().selectById(userId));
	}
	
	public void editSave() throws Exception
	{
		PostModel pm = DataAccessDriver.getInstance().newPostModel();
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		
		Post p = pm.selectById(Integer.parseInt(JForum.getRequest().getParameter("post_id")));
		p.setText(JForum.getRequest().getParameter("message"));
		p.setSubject(JForum.getRequest().getParameter("subject"));
		p.setBbCodeEnabled(JForum.getRequest().getParameter("disable_bbcode") != null ?  false : true);
		p.setHtmlEnabled(JForum.getRequest().getParameter("disable_html") != null ?  false : true);
		p.setSmiliesEnabled(JForum.getRequest().getParameter("disable_smilies") != null ?  false : true);
		p.setSignatureEnabled(JForum.getRequest().getParameter("attach_sig") != null ? true : false);
		p.setEditTime(new GregorianCalendar().getTimeInMillis());
		
		// The user wants to preview the message before posting it?
		if (JForum.getRequest().getParameter("preview") != null) {
			JForum.getContext().put("preview", true);
			Post postPreview = new Post(p);
			JForum.getContext().put("postPreview", this.preparePostText(postPreview));
			
			this.edit(true, p);
		}
		else {
			Topic t = tm.selectById(p.getTopicId());
			
			if (!this.shallProceed(t.getForumId())) {
				return;
			}
			
			if (t.getStatus() == Topic.STATUS_LOCKED && !SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_POST_EDIT)) {
				this.topicLocked();
				return;
			}
			
			pm.update(p);
			
			// Updates the topic title
			if (t.getFirstPostId() == p.getId()) {
				t.setTitle(p.getSubject());
				t.setType(Integer.parseInt(JForum.getRequest().getParameter("topic_type")));
				tm.update(t);
				ForumRepository.reloadForum(t.getForumId());
				TopicRepository.clearCache(t.getForumId());
				
				// RSS
				TopicRSS rss = new TopicRSS();
				if (rss.objectExists(Integer.toString(t.getId()))) {
					QueuedExecutor.getInstance().execute(new RSSTask(rss));
				}
			}
			
			if (JForum.getRequest().getParameter("notify") == null) {
				tm.removeSubscription(p.getTopicId(), SessionFacade.getUserSession().getUserId());
			}
			
			JForum.setRedirect(JForum.getRequest().getContextPath() +"/posts/list/"+ p.getTopicId() +".page#"+ p.getId());
		}
	}

	public void insertSave() throws Exception
	{
		Topic t = new Topic();
		t.setForumId(Integer.parseInt(JForum.getRequest().getParameter("forum_id")));
		
		if (!this.shallProceed(t.getForumId())) {
			return;
		}

		int topicId = -1;
		
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		PostModel pm = DataAccessDriver.getInstance().newPostModel();
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		
		if (JForum.getRequest().getParameter("topic_id") != null) {
			topicId = Integer.parseInt(JForum.getRequest().getParameter("topic_id"));
			t = tm.selectById(topicId);

			// Cannot insert new messages on locked topics
			if (t.getStatus() == Topic.STATUS_LOCKED) {
				this.topicLocked();
				return;
			}
		}
		
		if (JForum.getRequest().getParameter("topic_type") != null) {
			t.setType(Integer.parseInt(JForum.getRequest().getParameter("topic_type")));
		}
		
		UserSession us = SessionFacade.getUserSession();
		User u = new User();
		u.setId(us.getUserId());
		u.setUsername(us.getUsername());

		t.setPostedBy(u);
		
		// Set the Post
		Post p = new Post();
		p.setText(JForum.getRequest().getParameter("message"));
		p.setSubject(JForum.getRequest().getParameter("subject"));
		p.setBbCodeEnabled(JForum.getRequest().getParameter("disable_bbcode") != null ?  false : true);
		p.setHtmlEnabled(JForum.getRequest().getParameter("disable_html") != null ?  false : true);
		p.setSmiliesEnabled(JForum.getRequest().getParameter("disable_smilies") != null ?  false : true);
		p.setSignatureEnabled(JForum.getRequest().getParameter("attach_sig") != null ? true : false);
		p.setTime(new GregorianCalendar().getTimeInMillis());
		p.setUserId(us.getUserId());
		p.setForumId(Integer.parseInt(JForum.getRequest().getParameter("forum_id")));
		
		boolean preview = (JForum.getRequest().getParameter("preview") != null);
		if (!preview) {
			// If topic_id is -1, then is the first post
			if (topicId == -1) {		
				t.setTime(new GregorianCalendar().getTimeInMillis());
				t.setTitle(JForum.getRequest().getParameter("subject"));
				
				topicId = tm.addNew(t);
				t.setId(topicId);
				fm.incrementTotalTopics(t.getForumId(), 1);
			}
			else {
				tm.incrementTotalReplies(topicId);
				tm.incrementTotalViews(topicId);
				
				// Ok, we have an answer. Time to notify the subscribed users
				if (Boolean.valueOf((String)SystemGlobals.getValue("mail.notify.anwsers")) == Boolean.TRUE) {
					try {
						QueuedExecutor.getInstance().execute(new EmailSenderTask(new TopicSpammer(t, tm.notifyUsers(t))));
					}
					catch (Exception e) {
						// Shall we log the error?
					}
				}
			}
			
			// Topic watch
			if (!tm.isUserSubscribed(topicId, u.getId())) {
				if (JForum.getRequest().getParameter("notify") != null) {
					tm.subscribeUser(topicId, u.getId());
				}
			}
			
			p.setTopicId(topicId);
			
			// Save the remaining stuff
			int postId = pm.addNew(p);
			
			if (JForum.getRequest().getParameter("topic_id") == null) {
				t.setFirstPostId(postId);
			}
			
			t.setLastPostId(postId);
			tm.update(t);
			
			fm.setLastPost(t.getForumId(), postId);
			
			ForumRepository.reloadForum(t.getForumId());
			TopicRepository.clearCache(t.getForumId());

			JForum.setRedirect(JForum.getRequest().getContextPath() +"/posts/list/"+ topicId +".page#"+ postId);
			((HashMap)SessionFacade.getAttribute("topics_tracking")).put(new Integer(t.getId()), new Long(p.getTime()));

			// RSS
			QueuedExecutor.getInstance().execute(new RSSTask(new TopicRSS()));
		}
		else {
			JForum.getContext().put("preview", true);
			JForum.getContext().put("post", p);
			JForum.getContext().put("topic", t);
			
			Post postPreview = new Post(p);
			JForum.getContext().put("postPreview", this.preparePostText(postPreview));
			
			this.insert();
		}
	}
	
	public void delete() throws Exception
	{
		if (!SecurityRepository.canAccess(SecurityConstants.PERM_MODERATION_POST_REMOVE)) {
			JForum.getContext().put("moduleAction", "message.htm");
			JForum.getContext().put("message", I18n.getMessage("CannotRemovePost"));
			
			return;
		}
		
		// Post
		PostModel pm = DataAccessDriver.getInstance().newPostModel();
		Post p = pm.selectById(Integer.parseInt(JForum.getRequest().getParameter("post_id")));
		
		TopicModel tm = DataAccessDriver.getInstance().newTopicModel();
		Topic t = tm.selectById(p.getTopicId());
		
		if (!this.shallProceed(t.getForumId())) {
			return;
		}
		
		if (p.getId() == 0) {
			this.postNotFound();
			return;
		}
		
		pm.delete(p);
		
		// Topic
		tm.decrementTotalReplies(p.getTopicId());
		
		int maxPostId = tm.getMaxPostId(p.getTopicId());
		if (maxPostId > -1) {
			tm.setLastPostId(p.getTopicId(), maxPostId);
		}
		
		// Forum
		ForumModel fm = DataAccessDriver.getInstance().newForumModel();
		
		maxPostId = fm.getMaxPostId(p.getForumId());
		if (maxPostId > -1) {
			fm.setLastPost(p.getForumId(), maxPostId);
		}
		
		// It was the last remaining post in the topic?
		int totalPosts = tm.getTotalPosts(p.getTopicId());
		if (totalPosts > 0) {
			JForum.setRedirect(JForum.getRequest().getContextPath() +"/posts/list/"+ p.getTopicId() +".page");
		}
		else {
			// Ok, all posts were removed. Time to say goodbye
			Topic topic = new Topic();
			topic.setId(p.getTopicId());
			tm.delete(topic);

			tm.removeSubscriptionByTopic(p.getTopicId());
			
			fm.decrementTotalTopics(p.getForumId(), 1);
			
			JForum.setRedirect(JForum.getRequest().getContextPath() +"/forums/show/"+ p.getForumId() +".page");
		}
		
		ForumRepository.reloadForum(p.getForumId());
		
		// RSS
		TopicRSS rss = new TopicRSS();
		if (rss.objectExists(Integer.toString(p.getTopicId()))) {
			QueuedExecutor.getInstance().execute(new RSSTask(rss));
		}
	}
	
	public void unwatch() throws Exception
	{
		if (this.isUserLogged()) {
			int topicId = Integer.parseInt(JForum.getRequest().getParameter("topic_id"));
			int userId = SessionFacade.getUserSession().getUserId();
	
			DataAccessDriver.getInstance().newTopicModel().removeSubscription(topicId, userId);
			
			JForum.getContext().put("moduleAction", "message.htm");
			JForum.getContext().put("message", I18n.getMessage("ForumBase.unwatched"));
		}
		else {
			this.contextToLogin();
		}
	}
	
	private boolean isUserLogged()
	{
		return (SessionFacade.getAttribute("logged") != null && SessionFacade.getAttribute("logged").equals("1"));
	}
	
	private void contextToLogin()
	{
		JForum.getContext().put("moduleAction", "forum_login.htm");
		String uri = JForum.getRequest().getRequestURI();
		String query = JForum.getRequest().getQueryString();
		String path = query == null ? uri : uri +"?"+ query;
		
		JForum.getContext().put("returnPath",  path);
	}
	
	private void topicLocked()
	{
		JForum.getContext().put("moduleAction", "message.htm");
		JForum.getContext().put("message", I18n.getMessage("PostShow.topicLocked"));
	}
	
	private boolean anonymousPost(int forumId)
	{
		// Check if anonymous posts are allowed
		if (!this.isUserLogged() && !SecurityRepository.canAccess(SecurityConstants.PERM_ANONYMOUS_POST, Integer.toString(forumId))) {
			this.contextToLogin();
			
			return false;
		}
		
		return true;
	}
	
	private String processSmilies(String text, ArrayList smilies)
	{
		Iterator iter = smilies.iterator();
		while (iter.hasNext()) {
			Smilie s = (Smilie)iter.next();
			
			int index = text.indexOf(s.getCode());
			if (index > -1) {
				text = text.replaceAll("\\Q"+ s.getCode() +"\\E", s.getUrl());
			}
		}
		
		return text;
	}
	
	private Post preparePostText(Post p)
	{
		if (!p.isHtmlEnabled()) {
			p.setText(p.getText().replaceAll("<", "&lt;"));
		}
		
		// First of all, convert new lines to <br>'s
		p.setText(p.getText().replaceAll("\n", "<br>"));			

		// Then, search for bb codes
		if (p.isBbCodeEnabled()) {
			if (p.getText().indexOf('[') > -1 && p.getText().indexOf(']') > -1) {
				int openQuotes = 0;
				Iterator tmpIter = BBCodeRepository.getBBCollection().getBbList().iterator();
				
				while (tmpIter.hasNext()) {
					BBCode bb = (BBCode)tmpIter.next();
					
					// little hacks
					if (bb.getBeforeReplace() != null) {
						boolean keepGoing = true;
						
						while (keepGoing) {
							Matcher matcher = Pattern.compile(bb.getBeforeUseRegexp()).matcher(p.getText());
							keepGoing = matcher.matches();
							
							if (!keepGoing) {
								break;
							}

							String contents = matcher.group(1);
							contents = contents.replaceAll(bb.getBeforeReplace(), "&"+ bb.getBeforeReplaceWith());
							
							p.setText(p.getText().replaceFirst("\\Q"+ matcher.group(1) +"\\E", contents));
							p.setText(p.getText().replaceFirst(bb.getRegex(), bb.getReplace()));
						}
					}
					else {
						// More hacks
						if (bb.removeQuotes()) {
							Matcher matcher = Pattern.compile(bb.getRegex()).matcher(p.getText());
							
							while (matcher.find()) {
								String contents = matcher.group(1);
								contents = contents.replaceAll("'", "");
								contents = contents.replaceAll("\"", "");
								
								String replace = bb.getReplace().replaceAll("\\$1", contents);

								p.setText(p.getText().replaceFirst(bb.getRegex(), replace));
							}
						}
						else {
							// Another hack for the quotes
							if (bb.getTagName().equals("openQuote")) {
								Matcher matcher = Pattern.compile(bb.getRegex()).matcher(p.getText());								
								
								while (matcher.find()) {
									openQuotes++;
									
									p.setText(p.getText().replaceFirst(bb.getRegex(), bb.getReplace()));
								}
							}
							else if (bb.getTagName().equals("closeQuote")) {
								Matcher matcher = Pattern.compile(bb.getRegex()).matcher(p.getText());
								
								while (matcher.find()) {
									openQuotes--;
									
									p.setText(p.getText().replaceFirst(bb.getRegex(), bb.getReplace()));
								}
							}
							else {
								p.setText(p.getText().replaceAll(bb.getRegex(), bb.getReplace()));
							}
						}
						
					}
				}
				
				if (openQuotes > 0) {
					BBCode closeQuote = BBCodeRepository.findByName("closeQuote");
					
					// I'll not check for nulls ( but I should )
					for (int i = 0; i < openQuotes; i++) {
						p.setText(p.getText() + closeQuote.getReplace());
					}
				}
			}
		}
		
		// Smilies...
		if (p.isSmiliesEnabled()) {
			p.setText(this.processSmilies(p.getText(), SmiliesRepository.getSmilies()));
		}
		
		return p;
	}
}
