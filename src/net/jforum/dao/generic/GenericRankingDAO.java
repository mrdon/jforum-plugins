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
 * This file creation date: Mar 23, 2003 / 7:52:13 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.dao.generic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.jforum.JForumExecutionContext;
import net.jforum.entities.Ranking;
import net.jforum.exceptions.DatabaseException;
import net.jforum.util.DbUtils;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.Logger;

/**
 * @author Rafael Steil
 * @version $Id: GenericRankingDAO.java,v 1.7 2006/08/20 22:47:28 rafaelsteil Exp $
 */
public class GenericRankingDAO implements net.jforum.dao.RankingDAO 
{
    private final static Logger log = Logger.getLogger(GenericRankingDAO.class);

	/**
	 * @see net.jforum.dao.RankingDAO#selectById(int)
	 */
	public Ranking selectById(int rankingId)
	{
		Ranking ranking = new Ranking();
		
		PreparedStatement p=null;
        ResultSet rs=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("RankingModel.selectById"));
            p.setInt(1, rankingId);

            rs = p.executeQuery();
            if (rs.next()) {
                    ranking.setId(rankingId);
                    ranking.setTitle(rs.getString("rank_title"));
                    ranking.setImage(rs.getString("rank_image"));
                    ranking.setMin(rs.getInt("rank_min"));
                    ranking.setSpecial(rs.getInt("rank_special"));
            }

            return ranking;
        }
        catch (SQLException e) {
            String es = "Error selectById()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close(rs, p);
        }
    }

	/**
	 * @see net.jforum.dao.RankingDAO#selectAll()
	 */
	public List selectAll()
	{
		List l = new ArrayList();
		PreparedStatement p=null;
        ResultSet rs=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("RankingModel.selectAll"));
            rs = p.executeQuery();

            while (rs.next()) {
                Ranking ranking = new Ranking();

                ranking.setId(rs.getInt("rank_id"));
                ranking.setTitle(rs.getString("rank_title"));
                ranking.setImage(rs.getString("rank_image"));
                ranking.setMin(rs.getInt("rank_min"));
                ranking.setSpecial(rs.getInt("rank_special"));

                l.add(ranking);
            }

            return l;
        }
        catch (SQLException e) {
            String es = "Error selectAll()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close(rs, p);
        }
    }

	/** 
	 * @see net.jforum.dao.RankingDAO#delete(int)
	 */
	public void delete(int rankingId)
	{
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("RankingModel.delete"));
            p.setInt(1, rankingId);

            p.executeUpdate();
        }
        catch (SQLException e) {
            String es = "Error selectActiveBannerByPlacement()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close( p);
        }
	}

	/** 
	 * @see net.jforum.dao.RankingDAO#update(net.jforum.entities.Ranking)
	 */
	public void update(Ranking ranking)
	{
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("RankingModel.update"));

            p.setString(1, ranking.getTitle());
            p.setString(2, ranking.getImage());
            p.setInt(3, ranking.getSpecial());
            p.setInt(4, ranking.getMin());
            p.setInt(5, ranking.getId());

            p.executeUpdate();
        }
        catch (SQLException e) {
            String es = "Error update()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close( p);
        }
	}

	/** 
	 * @see net.jforum.dao.RankingDAO#addNew(net.jforum.entities.Ranking)
	 */
	public void addNew(Ranking ranking)
	{
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("RankingModel.addNew"));

            p.setString(1, ranking.getTitle());
            p.setInt(2, ranking.getMin());

            p.executeUpdate();
        }
        catch (SQLException e) {
            String es = "Error selectActiveBannerByPlacement()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close( p);
        }
	}

}
