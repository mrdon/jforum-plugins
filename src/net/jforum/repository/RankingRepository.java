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
 * This file creation date: 03/09/2003 / 23:42:55
 * net.jforum.repository.RankingRepository.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: RankingRepository.java,v 1.4 2004/11/12 03:08:11 rafaelsteil Exp $
 */
package net.jforum.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.jforum.ForumException;
import net.jforum.entities.Ranking;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.RankingModel;

/**
 * Repository for the ranks.
 * 
 * @author Rafael Steil
 */
public class RankingRepository 
{
	private static ArrayList ranksList = new ArrayList();
	
	static {
		try {
			loadRanks();
		}
		catch (Exception e) {
			throw new ForumException(e);
		}
	}
	
	public static void loadRanks() throws Exception
	{
		RankingModel rm = DataAccessDriver.getInstance().newRankingModel();
		RankingRepository.ranksList.clear();
		
		List l = rm.selectAll();
		int total = l.size();
		for (Iterator iter = l.iterator(); iter.hasNext(); ) {
			Ranking r = (Ranking)iter.next();
	
			RankingRepository.ranksList.add(r);
		}
	}
	
	/**
	 * Gets the title associated to total of messages the user have
	 * @param total Number of messages the user have. The ranking will be
	 * returned according to the range to which this total belongs to. 
	 * @return String with the ranking title. 
	 */	
	public static String getRankTitle(int total) 
	{
		Ranking lastRank = new Ranking();

		for (Iterator iter = RankingRepository.ranksList.iterator(); iter.hasNext(); ) {
			Ranking r = (Ranking)iter.next();
			
			if (total == r.getMin()) {
				return r.getTitle();
			}
			else if (total > lastRank.getMin() && total < r.getMin()) {
				return lastRank.getTitle();
			}
			
			lastRank = r;
		}
		
		return lastRank.getTitle();
	}
}
