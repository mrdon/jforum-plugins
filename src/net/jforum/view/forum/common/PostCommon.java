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
 * This file creation date: 21/05/2004 - 15:33:36
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jforum.JForum;
import net.jforum.SessionFacade;
import net.jforum.entities.Post;
import net.jforum.entities.Smilie;
import net.jforum.entities.User;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.PostModel;
import net.jforum.model.UserModel;
import net.jforum.repository.BBCodeRepository;
import net.jforum.repository.PostRepository;
import net.jforum.repository.SecurityRepository;
import net.jforum.repository.SmiliesRepository;
import net.jforum.security.SecurityConstants;
import net.jforum.util.SafeHtml;
import net.jforum.util.bbcode.BBCode;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: PostCommon.java,v 1.11 2005/03/04 14:14:47 rafaelsteil Exp $
 */
public class PostCommon
{
	public static Post preparePostForDisplay(Post p)
	{
		if (!p.isHtmlEnabled()) {
			p.setText(p.getText().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		}

		// Then, search for bb codes
		if (p.isBbCodeEnabled()) {
			p.setText(PostCommon.processText(p.getText()));
		}

		// Smilies...
		if (p.isSmiliesEnabled()) {
			p.setText(processSmilies(p.getText(), SmiliesRepository.getSmilies()));
		}

		return p;
	}
	
	private static String matchQuotedBB(BBCode bb, String text)
	{
		Matcher matcher = Pattern.compile(bb.getRegex()).matcher(text);

		while (matcher.find()) {
			String contents = matcher.group(1);
			contents = contents.replaceAll("'", "");
			contents = contents.replaceAll("\"", "");

			String replace = bb.getReplace().replaceAll("\\$1", contents);

			text = text.replaceFirst(bb.getRegex(), replace);
		}
		
		return text;
	}

	public static String processText(String text)
	{
		if (text == null) {
			return null;
		}

		// DO NOT remove the trailing blank space
		text = text.replaceAll("\n", "<br> ");
		
		Iterator iter = BBCodeRepository.getBBCollection().getAlwaysProcessList().iterator();
		while (iter.hasNext()) {
			text = PostCommon.matchQuotedBB((BBCode)iter.next(), text);
		}

		if (text.indexOf('[') > -1 && text.indexOf(']') > -1) {
			int openQuotes = 0;
			Iterator tmpIter = BBCodeRepository.getBBCollection().getBbList().iterator();

			while (tmpIter.hasNext()) {
				BBCode bb = (BBCode) tmpIter.next();

				// little hacks
				if (bb.removeQuotes()) {
					text = PostCommon.matchQuotedBB(bb, text);
				}
				else {
					// Another hack for the quotes
					if (bb.getTagName().equals("openQuote") || bb.getTagName().equals("openSimpleQuote")) {
						Matcher matcher = Pattern.compile(bb.getRegex()).matcher(text);

						while (matcher.find()) {
							openQuotes++;

							text = text.replaceFirst(bb.getRegex(), bb.getReplace());
						}
					}
					else if (bb.getTagName().equals("closeQuote")) {
						if (openQuotes > 0) {
							Matcher matcher = Pattern.compile(bb.getRegex()).matcher(text);

							while (matcher.find() && openQuotes-- > 0) {
								text = text.replaceFirst(bb.getRegex(), bb.getReplace());
							}
						}
					}
					else if (bb.getTagName().equals("code")) {
						Matcher matcher = Pattern.compile(bb.getRegex()).matcher(text);
						StringBuffer sb = new StringBuffer(text);

						while (matcher.find()) {
							String contents = matcher.group(1);

							// Firefox seems to interpret <br> inside <pre>,
							// so we need this bizarre workaround
							contents = contents.replaceAll("<br>", "\n");

							// Do not allow other bb tags inside "code"
							contents = contents.replaceAll("\\[", "&#91;").replaceAll("\\]", "&#93;");

							// Try to bypass smilies interpretation
							contents = contents.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");

							// XML-like tags
							contents = contents.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

							StringBuffer replace = new StringBuffer(bb.getReplace());
							int index = replace.indexOf("$1");
							if (index > -1) {
								replace.replace(index, index + 2, contents);
							}

							index = sb.indexOf("[code]");
							int lastIndex = sb.indexOf("[/code]") + "[/code]".length();

							if (lastIndex > index) {
								sb.replace(index, lastIndex, replace.toString());
								text = sb.toString();
							}
						}
					}
					else {
						text = text.replaceAll(bb.getRegex(), bb.getReplace());
					}
				}
			}

			if (openQuotes > 0) {
				BBCode closeQuote = BBCodeRepository.findByName("closeQuote");

				for (int i = 0; i < openQuotes; i++) {
					text = text + closeQuote.getReplace();
				}
			}
		}

		return text;
	}

	public static String processSmilies(String text, List smilies)
	{
		if (text == null || text.equals("")) {
			return text;
		}

		Iterator iter = smilies.iterator();
		while (iter.hasNext()) {
			Smilie s = (Smilie) iter.next();

			int index = text.indexOf(s.getCode());
			if (index > -1) {
				text = text.replaceAll("\\Q" + s.getCode() + "\\E", s.getUrl());
			}
		}

		return text;
	}

	public static Post fillPostFromRequest() throws Exception
	{
		Post p = new Post();
		p.setTime(new Date());

		return fillPostFromRequest(p, false);
	}

	public static Post fillPostFromRequest(Post p, boolean isEdit) throws Exception
	{
		p.setSubject(JForum.getRequest().getParameter("subject"));
		p.setBbCodeEnabled(JForum.getRequest().getParameter("disable_bbcode") != null ? false : true);
		p.setSmiliesEnabled(JForum.getRequest().getParameter("disable_smilies") != null ? false : true);
		p.setSignatureEnabled(JForum.getRequest().getParameter("attach_sig") != null ? true : false);
		p.setUserIp(JForum.getRequest().getRemoteAddr());

		if (!isEdit) {
			p.setUserId(SessionFacade.getUserSession().getUserId());
		}
		
		boolean htmlEnabled = SecurityRepository.canAccess(SecurityConstants.PERM_HTML_DISABLED, JForum.getRequest()
				.getParameter("forum_id"));
		p.setHtmlEnabled(htmlEnabled && JForum.getRequest().getParameter("disable_html") == null);

		if (p.isHtmlEnabled()) {
			p.setText(SafeHtml.makeSafe(JForum.getRequest().getParameter("message")));
		}
		else {
			p.setText(JForum.getRequest().getParameter("message"));
		}

		return p;
	}

	public static void addToTopicPosters(int userId, Map usersMap, UserModel um) throws Exception
	{
		Integer posterId = new Integer(userId);
		if (!usersMap.containsKey(posterId)) {
			usersMap.put(posterId, PostCommon.getUserForDisplay(userId));
		}
	}

	public static User getUserForDisplay(int userId) throws Exception
	{
		User u = DataAccessDriver.getInstance().newUserModel().selectById(userId);
		u.setSignature(PostCommon.processText(u.getSignature()));
		u.setSignature(PostCommon.processSmilies(u.getSignature(), SmiliesRepository.getSmilies()));

		return u;
	}

	public static List topicPosts(PostModel pm, UserModel um, Map usersMap, boolean canEdit, int userId, int topicId,
			int start, int count) throws Exception
	{
		List posts = null;
		boolean needPrepare = true;
		
 		if (SystemGlobals.getBoolValue(ConfigKeys.POSTS_CACHE_ENABLED)) {
 			posts = PostRepository.selectAllByTopicByLimit(topicId, start, count);
 			needPrepare = false;
 		}
 		else {
 			posts = pm.selectAllByTopicByLimit(topicId, start, count);
 		}
 		
		List helperList = new ArrayList();

		int anonymousUser = SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID);

		for (Iterator iter = posts.iterator(); iter.hasNext(); ) {
			Post p;
			if (needPrepare) {
				p = (Post)iter.next();
			}
			else {
				p = new Post((Post)iter.next());
			}
			
			if (canEdit || (p.getUserId() != anonymousUser && p.getUserId() == userId)) {
				p.setCanEdit(true);
			}

			PostCommon.addToTopicPosters(p.getUserId(), usersMap, um);
			helperList.add(needPrepare ? PostCommon.preparePostForDisplay(p) : p);
		}

		return helperList;
	}
}
