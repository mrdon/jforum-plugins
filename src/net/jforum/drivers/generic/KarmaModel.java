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
 * Created on Jan 11, 2005 11:22:19 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.generic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import net.jforum.JForum;
import net.jforum.entities.Karma;
import net.jforum.entities.KarmaStatus;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: KarmaModel.java,v 1.3 2005/01/21 15:51:21 rafaelsteil Exp $
 */
public class KarmaModel implements net.jforum.model.KarmaModel
{
	/**
	 * @see net.jforum.model.KarmaModel#addKarma(net.jforum.entities.Karma)
	 */
	public void addKarma(Karma karma) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.add"));
		p.setInt(1, karma.getPostId());
		p.setInt(2, karma.getPostUserId());
		p.setInt(3, karma.getFromUserId());
		p.setInt(4, karma.getPoints());
		p.setInt(5, karma.getTopicId());
		p.executeUpdate();
		p.close();
		
		this.updateUserKarma(karma.getPostUserId());
	}

	/**
	 * @see net.jforum.model.KarmaModel#selectKarmaStatus(int)
	 */
	public KarmaStatus getUserKarma(int userId) throws Exception
	{
		KarmaStatus status = new KarmaStatus();
		
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.getUserKarma"));
		p.setInt(1, userId);
		
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			status.setKarmaPoints(Math.round(rs.getDouble("user_karma")));
		}
		
		rs.close();
		p.close();
		
		return status;
	}
	
	/**
	 * @see net.jforum.model.KarmaModel#updateUserKarma(int)
	 */
	public void updateUserKarma(int userId) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.getUserKarmaPoints"));
		p.setInt(1, userId);
		
		int totalRecords = 0;
		double totalPoints = 0;
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			int points = rs.getInt("points");
			int votes = rs.getInt("votes");

			totalPoints += ((double)points / votes);
			totalRecords++;
		}

		rs.close();
		p.close();
		
		p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.updateUserKarma"));
		p.setDouble(1, (double)totalPoints / totalRecords);
		p.setInt(2, userId);
		p.executeUpdate();
		p.close();
	}

	/**
	 * @see net.jforum.model.KarmaModel#update(net.jforum.entities.Karma)
	 */
	public void update(Karma karma) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.update"));
		p.setInt(1, karma.getPoints());
		p.setInt(2, karma.getId());
		p.executeUpdate();
		p.close();
	}
	
	/**
	 * @see net.jforum.model.KarmaModel#getPostKarma(int)
	 */
	public KarmaStatus getPostKarma(int postId) throws Exception
	{
		KarmaStatus karma = new KarmaStatus();

		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.getPostKarma"));
		p.setInt(1, postId);
		
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			karma.setKarmaPoints(Math.round(rs.getDouble(1)));
		}
		
		rs.close();
		p.close();
		
		return karma;
	}
	
	/**
	 * @see net.jforum.model.KarmaModel#userCanAddKarma(int, int)
	 */
	public boolean userCanAddKarma(int userId, int postId) throws Exception
	{
		boolean status = true;

		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.userCanAddKarma"));
		p.setInt(1, postId);
		p.setInt(2, userId);
		
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			status = rs.getInt(1) < 1;
		}
		
		rs.close();
		p.close();
		
		return status;
	}
	
	/**
	 * @see net.jforum.model.KarmaModel#getUserVotes(int, int)
	 */
	public Map getUserVotes(int topicId, int userId) throws Exception
	{
		Map m = new HashMap();
		
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.getUserVotes"));
		p.setInt(1, topicId);
		p.setInt(2, userId);
		
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			m.put(new Integer(rs.getInt("post_id")), new Integer(rs.getInt("points")));
		}
		
		rs.close();
		p.close();
		
		return m;
	}
}
