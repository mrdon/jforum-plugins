/*
 * Copyright (c) Rafael Steil
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
 * This file creation date: 02/04/2004 - 20:31:35
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.jforum.Command;
import net.jforum.JForumExecutionContext;
import net.jforum.SessionFacade;
import net.jforum.entities.User;
import net.jforum.exceptions.RequestEmptyException;
import net.jforum.repository.BBCodeRepository;
import net.jforum.repository.ModulesRepository;
import net.jforum.repository.SmiliesRepository;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.preferences.TemplateKeys;
import freemarker.template.SimpleHash;

/**
 * @author Rafael Steil
 * @version $Id: ViewCommon.java,v 1.18 2006/08/06 00:07:44 rafaelsteil Exp $
 */
public final class ViewCommon
{
	/**
	 * Prepared the user context to use data pagination. 
	 * The following variables are set to the context:
	 * <p>
	 * 	<ul>
	 * 		<li> <i>totalPages</i> - total number of pages
	 * 		<li> <i>recordsPerPage</i> - how many records will be shown on each page
	 * 		<li> <i>totalRecords</i> - number of records fount
	 * 		<li> <i>thisPage</i> - the current page being shown
	 * 		<li> <i>start</i> - 
	 * 	</ul>
	 * </p>
	 * @param start
	 * @param totalRecords
	 * @param recordsPerPage
	 */
	public static void contextToPagination(int start, int totalRecords, int recordsPerPage)
	{
		SimpleHash context = JForumExecutionContext.getTemplateContext();
		
		context.put("totalPages", new Double(Math.ceil((double) totalRecords / (double) recordsPerPage)));
		context.put("recordsPerPage", new Integer(recordsPerPage));
		context.put("totalRecords", new Integer(totalRecords));
		context.put("thisPage", new Double(Math.ceil((double) (start + 1) / (double) recordsPerPage)));
		context.put("start", new Integer(start));
	}
	
	public static String contextToLogin() 
	{
		String uri = JForumExecutionContext.getRequest().getRequestURI();
		String query = JForumExecutionContext.getRequest().getQueryString();
		String path = query == null ? uri : uri + "?" + query;
		
		JForumExecutionContext.getTemplateContext().put("returnPath", path);
		
		if (ConfigKeys.TYPE_SSO.equals(SystemGlobals.getValue(ConfigKeys.AUTHENTICATION_TYPE))) {
			String redirect = SystemGlobals.getValue(ConfigKeys.SSO_REDIRECT);
			
			if (redirect != null && redirect.trim().length() > 0) {
				JForumExecutionContext.setRedirect(JForumExecutionContext.getRequest().getContextPath() + redirect.trim() + path);
			}
		}
		
		return TemplateKeys.USER_LOGIN;
	}
	
	/**
	 * Returns the initial page to start fetching records from.
	 *   
	 * @return The initial page number
	 */
	public static int getStartPage()
	{
		String s = JForumExecutionContext.getRequest().getParameter("start");
		int start = 0;
		
		if (s == null || s.trim().equals("")) {
			start = 0;
		}
		else {
			start = Integer.parseInt(s);
			
			if (start < 0) {
				start = 0;
			}
		}
		
		return start;
	}
	
	/**
	 * Gets the forum base link.
	 * The returned link has a trailing slash
	 * @return The forum link, with the trailing slash
	 */
	public static String getForumLink()
	{
		String forumLink = SystemGlobals.getValue(ConfigKeys.FORUM_LINK);
		
		if (forumLink.charAt(forumLink.length() - 1) != '/') {
			forumLink += "/";
		}
		
		return forumLink;
	}

	/**
	 * Checks if some request needs to be reprocessed. 
	 * This is likely to happen when @link net.jforum.ActionServletRequest#dumpRequest()
	 * is stored in the session. 
	 * 
	 * @return <code>true</code> of <code>false</code>, depending of the status.
	 */
	public static boolean needReprocessRequest()
	{
		return (SessionFacade.getAttribute(ConfigKeys.REQUEST_DUMP) != null);
	}
	
	/**
	 * Reprocess a request. 
	 * The request data should be in the session, held by the key
	 * <code>ConfigKeys.REQUEST_DUMP</code> and the value as
	 * a <code>java.util.Map</code>. Usual behaviour is to have the return
	 * of @link net.jforum.ActionServletRequest#dumpRequest().
	 * @throws Exception, RequestEmptyException
	 */
	public static void reprocessRequest() throws Exception
	{
		Map data = (Map)SessionFacade.getAttribute(ConfigKeys.REQUEST_DUMP);
		if (data == null) {
			throw new RequestEmptyException("A call to ViewCommon#reprocessRequest() was made, but no data found");
		}
		
		String module = (String)data.get("module");
		String action = (String)data.get("action");
		
		if (module == null || action == null) {
			throw new RequestEmptyException("A call to ViewCommon#reprocessRequest() was made, "
				+ "but no module or action name was found");
		}
		
		JForumExecutionContext.getRequest().restoreDump(data);
		SessionFacade.removeAttribute(ConfigKeys.REQUEST_DUMP);
		
		String moduleClass = ModulesRepository.getModuleClass(module);
		((Command)Class.forName(moduleClass).newInstance()).process(JForumExecutionContext.getRequest(),
				JForumExecutionContext.getResponse(), JForumExecutionContext.getTemplateContext());
	}

	public static String toUtf8String(String s)
	{
		StringBuffer sb = new StringBuffer();
	
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
	
			if ((c >= 0) && (c <= 255)) {
				sb.append(c);
			}
			else {
				byte[] b;
	
				try {
					b = Character.toString(c).getBytes("utf-8");
				}
				catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
	
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
	
					if (k < 0) {
						k += 256;
					}
	
					sb.append('%').append(Integer.toHexString(k).toUpperCase());
				}
			}
		}
	
		return sb.toString();
	}
	
	/**
	 * Formats a date using the pattern defined in the configuration file.
	 * The key is the value of {@link ConfigKeys.DATE_TIME_FORMAT}
	 * @param date the date to format
	 * @return the string with the formated date
	 */
	public static String formatDate(Date date) 
	{
		SimpleDateFormat df = new SimpleDateFormat(SystemGlobals.getValue(ConfigKeys.DATE_TIME_FORMAT));
		return df.format(date);
	}
	
	/**
	 * Escapes &lt; by &amp;lt; and &gt; by &amp;gt;
	 * @param contents the string to parse
	 * @return the new string
	 */
	public static String espaceHtml(String contents)
	{
		StringBuffer sb = new StringBuffer(contents);
		
		replaceAll(sb, "<", "&lt");
		replaceAll(sb, ">", "&gt;");
		
		return sb.toString();
	}
	
	/**
	 * Replaces some string with another value
	 * @param sb the StrinbBuilder with the contents to work on
	 * @param what the string to be replaced
	 * @param with the new value
	 * @return the new string
	 */
	public static String replaceAll(StringBuffer sb, String what, String with)
	{
		int pos = sb.indexOf(what);
		
		while (pos > -1) {
			sb.replace(pos, pos + what.length(), with);
			pos = sb.indexOf(what);
		}
		
		return sb.toString();
	}
	
	/**
	 * @see #replaceAll(StringBuffer, String, String)
	 */
	public static String replaceAll(String contents, String what, String with)
	{
		return replaceAll(new StringBuffer(contents), what, with);
	}

	/**
	 * Parse the user's signature, to make it proper to visualization
	 * @param u the user instance
	 * @throws Exception
	 */
	public static void prepareUserSignature(User u) throws Exception
	{
		if (u.getSignature() != null) {
			StringBuffer sb = new StringBuffer(u.getSignature());
			
			replaceAll(sb, "\n", "<br/>");
			
			u.setSignature(PostCommon.alwaysProcess(u.getSignature(), BBCodeRepository.getBBCollection().getAlwaysProcessList()));
			u.setSignature(PostCommon.processText(u.getSignature()));
			u.setSignature(PostCommon.processSmilies(new StringBuffer(u.getSignature()), SmiliesRepository.getSmilies()));
		}
	}
}
