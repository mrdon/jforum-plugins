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

import net.jforum.JForum;
import net.jforum.entities.Karma;
import net.jforum.entities.KarmaStatus;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: KarmaModel.java,v 1.1 2005/01/13 23:30:17 rafaelsteil Exp $
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
		p.setInt(1, karma.getUserId());
		p.setInt(2, karma.getFromUserId());
		p.setInt(3, karma.isKarmaPositive() ? 1 : 0);
		p.executeUpdate();
		p.close();
	}

	/**
	 * @see net.jforum.model.KarmaModel#selectKarmaStatus(int)
	 */
	public KarmaStatus selectKarmaStatus(int userId) throws Exception
	{
		KarmaStatus status = null;
		
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.selectPositiveStatus"));
		p.setInt(1, userId);
		
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			int totalPositive = rs.getInt("positive");
			rs.close();
			p.close();
			
			p = JForum.getConnection().prepareStatement(
					SystemGlobals.getSql("KarmaModel.selectNegativeStatus"));
			p.setInt(1, userId);
			
			// We're assuming that it will move next
			rs = p.executeQuery();
			rs.next();
			int totalNegative = rs.getInt("negative");
			
			status = new KarmaStatus(userId, totalPositive, totalNegative);
		}
		
		rs.close();
		p.close();
		
		return status;
	}

	/**
	 * @see net.jforum.model.KarmaModel#update(net.jforum.entities.Karma)
	 */
	public void update(Karma karma) throws Exception
	{
		PreparedStatement p = JForum.getConnection().prepareStatement(
				SystemGlobals.getSql("KarmaModel.update"));
		p.setInt(1, karma.isKarmaPositive() ? 1 : 0);
		p.setInt(2, karma.getId());
		p.executeUpdate();
		p.close();
	}
}
