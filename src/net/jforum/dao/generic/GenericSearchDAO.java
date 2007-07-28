/*
 * Copyright (c) JForum Team
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
 * This file creation date: 25/02/2004 - 19:32:42
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.dao.generic;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import net.jforum.JForumExecutionContext;
import net.jforum.SessionFacade;
import net.jforum.dao.SearchArgs;
import net.jforum.dao.SearchDAO;
import net.jforum.exceptions.DatabaseException;
import net.jforum.exceptions.SearchException;
import net.jforum.repository.ForumRepository;
import net.jforum.util.DbUtils;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: GenericSearchDAO.java,v 1.25 2007/07/28 14:17:10 rafaelsteil Exp $
 */
public class GenericSearchDAO implements SearchDAO
{
	/**
	 * @see net.jforum.dao.SearchDAO#search(net.jforum.dao.SearchArgs)
	 */
	public List search(SearchArgs sd)
	{
		PreparedStatement p = null;
		try {
			// Check for the search cache
			if (!sd.getSearchStarted()) {
				this.cleanSearch();

				if (sd.getTime() == null) {
					throw new SearchException("This search operation requires a time period");
				}
				
				this.topicsByTime(sd);
			}

			String sql = SystemGlobals.getSql("SearchModel.searchBase").replaceAll(":fids:",
				ForumRepository.getListAllowedForums());
			StringBuffer criterias = new StringBuffer(512);

			if (sd.getOrderByField() == null || sd.getOrderByField().equals("")) {
				sd.setOrderByField("p.post_time");
			}

			// Prepare the query
			sql = sql.replaceAll(":orderByField:", sd.getOrderByField());
			sql = sql.replaceAll(":orderBy:", sd.getOrderBy());
			sql = sql.replaceAll(":criterias:", criterias.toString());

			p = JForumExecutionContext.getConnection().prepareStatement(sql);
			p.setString(1, SessionFacade.getUserSession().getSessionId());

			List list = new GenericTopicDAO().fillTopicsData(p);
			
			return list;
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
		}
		finally {
			DbUtils.close(p);
		}
	}

	// Find topics by time
	private void topicsByTime(SearchArgs args) throws SQLException
	{
		PreparedStatement p = null;
		
		try {
			p = JForumExecutionContext.getConnection().prepareStatement(
					SystemGlobals.getSql("SearchModel.searchByTime").replaceAll(":fids:",
							ForumRepository.getListAllowedForums()));
			p.setString(1, SessionFacade.getUserSession().getSessionId());
			p.setTimestamp(2, new Timestamp(args.getTime().getTime()));
			p.executeUpdate();

			this.selectTopicData();
		}
		finally {
			DbUtils.close(p);
		}
	}

	private void selectTopicData() throws SQLException
	{
		PreparedStatement p = null;
		try {
			p = JForumExecutionContext.getConnection().prepareStatement(
					SystemGlobals.getSql("SearchModel.selectTopicData"));
			p.setString(1, SessionFacade.getUserSession().getSessionId());
			p.setString(2, SessionFacade.getUserSession().getSessionId());
			p.executeUpdate();
		}
		finally {
			DbUtils.close(p);
		}
	}

	/**
	 * @see net.jforum.dao.SearchDAO#cleanSearch()
	 */
	public void cleanSearch()
	{
		PreparedStatement p = null;
		try {
			p = JForumExecutionContext.getConnection().prepareStatement(
					SystemGlobals.getSql("SearchModel.cleanSearchTopics"));
			p.setString(1, SessionFacade.getUserSession().getSessionId());
			p.executeUpdate();
			p.close();
			p = null;

			p = JForumExecutionContext.getConnection().prepareStatement(
				SystemGlobals.getSql("SearchModel.cleanSearchResults"));
			p.setString(1, SessionFacade.getUserSession().getSessionId());
			p.executeUpdate();
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
		}
		finally {
			DbUtils.close(p);
		}
	}
}
