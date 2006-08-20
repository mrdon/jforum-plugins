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
 * This file creation date: 25/02/2004 - 19:32:42
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.dao.generic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.jforum.JForumExecutionContext;
import net.jforum.SessionFacade;
import net.jforum.dao.SearchData;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.DbUtils;
import net.jforum.repository.ForumRepository;

import org.apache.log4j.Logger;

/**
 * @author Rafael Steil
 * @version $Id: GenericSearchDAO.java,v 1.17 2006/08/20 12:19:04 sergemaslyukov Exp $
 */
public class GenericSearchDAO implements net.jforum.dao.SearchDAO	
{
	private static final Logger log = Logger.getLogger(GenericSearchDAO.class);
	
	/** 
	 * @see net.jforum.dao.SearchDAO#search(net.jforum.dao.SearchData)
	 */
	public List search(SearchData sd)
	{
        PreparedStatement p=null;
        try
        {
            // Check for the search cache
            if (!sd.getSearchStarted()) {
                if (sd.getTime() == null) {
                    this.topicsByKeyword(sd);
                }
                else {
                    this.topicsByTime(sd);
                }
            }

            String sql = SystemGlobals.getSql("SearchModel.searchBase").replaceAll(":fids:",ForumRepository.getListAllowedForums());
            StringBuffer criterias = new StringBuffer(512);

            if (sd.getForumId() != 0) {
                criterias.append(" AND t.forum_id = ").append( sd.getForumId());
            }

            if (sd.getCategoryId() != 0) {
                sql = sql.replaceAll(":table_category:", ", jforum_forums f");

                criterias.append(" AND f.categories_id = ").append( sd.getCategoryId());
                criterias.append(" AND t.forum_id = f.forum_id");
            }
            else {
                sql = sql.replaceAll(":table_category:", "");
            }

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
            p=null;
            return list;
        }
        catch (SQLException e) {
            String es = "Erorr search()";
            log.error(es, e);
            throw new RuntimeException(es, e);
        }
        finally {
            DbUtils.close( p);
        }
    }
	
	// Find topics by time
	private void topicsByTime(SearchData sd) throws SQLException
	{
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.searchByTime").replaceAll(":fids:", ForumRepository.getListAllowedForums()));
            p.setString(1, SessionFacade.getUserSession().getSessionId());
            p.setTimestamp(2, new Timestamp(sd.getTime().getTime()));
            p.executeUpdate();

            this.selectTopicData();
        }
        finally {
            DbUtils.close(p);
        }
    }
	
	// Given a set of keywords, find the topics
	private void topicsByKeyword(SearchData sd) throws SQLException
    {
		boolean isLike = "like".equals(SystemGlobals.getValue(ConfigKeys.SEARCH_WORD_MATCHING).trim());
		
		String sql = isLike 
			? SystemGlobals.getSql("SearchModel.searchByLikeWord")
			: SystemGlobals.getSql("SearchModel.searchByWord");
		
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(sql);

            Map eachWordMap = new HashMap();

            int maxWordSize = SystemGlobals.getIntValue(ConfigKeys.SEARCH_MAX_WORD_SIZE);

            // Get the post ids to which the words are associated to
            for (int i = 0; i < sd.getKeywords().length; i++) {
                String word = sd.getKeywords()[i].toLowerCase();

                if (word.length() > maxWordSize) {
                    //truncate the word
                    word = word.substring(0, maxWordSize);
                }

                if (isLike) {
                    p.setString(1, "%" + word + "%");
                }
                else {
                    p.setString(1, word);
                }

                Set postsIds = new HashSet();
                ResultSet rs=null;
                try
                {
                    rs = p.executeQuery();

                    while (rs.next()) {
                        postsIds.add(new Integer(rs.getInt("post_id")));
                    }
                }
                finally
                {
                    DbUtils.close(rs);
                }

                if (postsIds.size() > 0) {
                    eachWordMap.put(sd.getKeywords()[i], postsIds);
                }
            }

            p.close();
            p=null;

            // [wordName] = { each, post, id }

            // If seach type is OR, then get all words
            // If it is AND, then we want only the ids common to all words
            Set postsIds = null;

            if (sd.getUseAllWords()) {
                for (Iterator iter = eachWordMap.values().iterator(); iter.hasNext(); ) {
                    if (postsIds == null) {
                        postsIds = new HashSet(eachWordMap.values().size());
                        postsIds.addAll((HashSet)iter.next());
                    }
                    else {
                        postsIds.retainAll((HashSet)iter.next());
                    }
                }
            }
            else {
                postsIds = new HashSet();

                for (Iterator iter = eachWordMap.values().iterator(); iter.hasNext(); ) {
                    postsIds.addAll((HashSet)iter.next());
                }
            }

            if (postsIds == null || postsIds.size() == 0) {
                return;
            }

            // Time to get ready to search for the topics ids
            StringBuffer sb = new StringBuffer(1024);
            for (Iterator iter = postsIds.iterator(); iter.hasNext(); ) {
                sb.append(iter.next()).append(",");
            }
            sb.delete(sb.length() - 1, sb.length());

            // Search for the ids, inserting them in the helper table
            sql = SystemGlobals.getSql("SearchModel.insertTopicsIds");
            sql = sql.replaceAll(":posts:", sb.toString());
            sql = sql.replaceAll(":fids:",ForumRepository.getListAllowedForums());

            p = JForumExecutionContext.getConnection().prepareStatement(sql);
            p.setString(1, SessionFacade.getUserSession().getSessionId());

            p.executeUpdate();

            // Now that we have the topics ids, it's time to make a copy from the
            // topics table, to make the search faster
            this.selectTopicData();

        }
        catch (SQLException e) {
            String es = "Erorr topicsByKeyword()";
            log.error(es, e);
            throw new RuntimeException(es, e);
        }
        finally {
            DbUtils.close( p);
        }
    }
	
	private void selectTopicData() throws SQLException
	{
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.selectTopicData"));
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
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.cleanSearchTopics"));
            p.setString(1, SessionFacade.getUserSession().getSessionId());
            p.executeUpdate();
            p.close();
            p=null;

            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("SearchModel.cleanSearchResults"));
            p.setString(1, SessionFacade.getUserSession().getSessionId());
            p.executeUpdate();
        }
        catch (SQLException e) {
            String es = "Erorr cleanSearch()";
            log.error(es, e);
            throw new RuntimeException(es, e);
        }
        finally {
            DbUtils.close( p);
        }
    }
}
