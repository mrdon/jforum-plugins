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
 * Created on 21/05/2004 - 14:19:11
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.dao.generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import net.jforum.JForumExecutionContext;
import net.jforum.dao.PollDAO;
import net.jforum.entities.Poll;
import net.jforum.entities.PollOption;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author David Almilli
 * @version $Id: GenericPollDAO.java,v 1.4 2006/01/29 15:06:24 rafaelsteil Exp $
 */
public class GenericPollDAO extends AutoKeys implements PollDAO {

	/**
	 * @see net.jforum.dao.PollDAO#addNew(net.jforum.entities.Poll)
	 */
	public int addNew(Poll poll) throws Exception {
		this.addNewPoll(poll);
		this.addNewPollOptions(poll.getId(), poll.getOptions());
		
		return poll.getId();
	}
	
	protected void addNewPoll(Poll poll) throws Exception {
		PreparedStatement p = this.getStatementForAutoKeys("PollModel.addNewPoll");
		p.setInt(1, poll.getTopicId());
		p.setString(2, poll.getLabel());
		p.setInt(3, poll.getLength());

		this.setAutoGeneratedKeysQuery(SystemGlobals.getSql("PollModel.lastGeneratedPollId"));
		int pollId = this.executeAutoKeysQuery(p);
		poll.setId(pollId);
		
		p.close();
	}
	
	protected void addNewPollOptions(int pollId, List options) throws Exception {
		Connection connection = JForumExecutionContext.getConnection();
		
		PreparedStatement p = connection.prepareStatement(SystemGlobals.getSql("PollModel.addNewPollOption"));
		PreparedStatement max = connection.prepareStatement(SystemGlobals.getSql("PollModel.selectMaxVoteId"));
		
		max.setInt(1, pollId);
		ResultSet rs = max.executeQuery();
		rs.next();
		
		int optionId = rs.getInt(1);
		
		rs.close();
		max.close();
		
		for (Iterator iter = options.iterator(); iter.hasNext(); ) {
			PollOption option = (PollOption)iter.next();
			
			p.setInt(1, pollId);
			p.setInt(2, ++optionId);
			p.setString(3, option.getText());
			
			p.executeUpdate();
		}
		
		p.close();
	}
	
	/**
	 * @see net.jforum.dao.PollDAO#selectById(int)
	 */
	public Poll selectById(int pollId) throws Exception {
		PreparedStatement p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.selectById"));		
		PreparedStatement o = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.selectOptionsByPollId"));		
		
		p.setInt(1, pollId);
		o.setInt(1, pollId);
		
		ResultSet prs = p.executeQuery();
		
		Poll poll = null;
		
		if (prs.next()) {
			poll = this.makePoll(prs);
			
			ResultSet ors = o.executeQuery();
			
			while (ors.next()) {
				poll.addOption(this.makePollOption(ors));
			}
			
			ors.close();
		}
		
		prs.close();
		p.close();
		o.close();
		
		return poll;
	}
	
	protected Poll makePoll(ResultSet rs) throws Exception {
		Poll poll = new Poll();
		poll.setId(rs.getInt("vote_id"));
		poll.setTopicId(rs.getInt("topic_id"));
		poll.setLabel(rs.getString("vote_text"));
		poll.setStartTime(rs.getTimestamp("vote_start"));
		poll.setLength(rs.getInt("vote_length"));
		
		return poll;
	}
	
	protected PollOption makePollOption(ResultSet rs) throws Exception {
		PollOption option = new PollOption();
		option.setPollId(rs.getInt("vote_id"));
		option.setId(rs.getInt("vote_option_id"));
		option.setText(rs.getString("vote_option_text"));
		option.setVoteCount(rs.getInt("vote_result"));
		
		return option;
	}

	/**
	 * @see net.jforum.dao.PollDAO#voteOnPoll(int, int, int, java.lang.String)
	 */
	public void voteOnPoll(int pollId, int optionId, int userId, String ipAddress) throws Exception {
		Connection connection = JForumExecutionContext.getConnection();
		
		PreparedStatement p = connection.prepareStatement(SystemGlobals.getSql("PollModel.incrementVoteCount"));
		PreparedStatement v = connection.prepareStatement(SystemGlobals.getSql("PollModel.addNewVoter"));
		
		p.setInt(1, pollId);
		p.setInt(2, optionId);
		
		v.setInt(1, pollId);
		v.setInt(2, userId);
		v.setString(3, ipAddress);
		
		p.executeUpdate();
		v.executeUpdate();
		
		p.close();
		v.close();
	}
	
	/**
	 * @see net.jforum.dao.PollDAO#hasVotedOnPoll(int, int)
	 */
	public boolean hasUserVotedOnPoll(int pollId, int userId) throws Exception {
		PreparedStatement p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.selectVoter"));
		p.setInt(1, pollId);
		p.setInt(2, userId);
		
		ResultSet rs = p.executeQuery();
		
		boolean hasVotedOnPoll = rs.next();
		
		rs.close();
		p.close();
		
		return hasVotedOnPoll;
	}

	
	/**
	 * Tells if the anonymous user has already voted on the given poll from the given IP
	 * @param pollId the poll id that is being checked
	 * @param ipAddress the IP address of the anonymoususer to check the vote for
	 * @return true if the user has already voted on the given poll
	 * @throws Exception
	 */
	public boolean hasUserVotedOnPoll(int pollId, String ipAddress) throws Exception {
		PreparedStatement p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.selectVoterByIP"));
		p.setInt(1, pollId);
		p.setString(2, ipAddress);
		
		ResultSet rs = p.executeQuery();
		
		boolean hasVotedOnPoll = rs.next();
		
		rs.close();
		p.close();
		
		return hasVotedOnPoll;
	}
	
	/**
	 * @see net.jforum.dao.PollDAO#delete(int)
	 */
	public void deleteByTopicId(int topicId) throws Exception {
		//first, lookup the poll id, then delete it
		PreparedStatement p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.selectPollByTopicId"));
		
		p.setInt(1, topicId);
		
		ResultSet rs = p.executeQuery();
		
		int pollId = 0;
		if (rs.next()) {
			pollId = rs.getInt("vote_id");
		}
		rs.close();
		p.close();
		
		if (pollId != 0) {
			delete(pollId);
		}
	}
	
	/**
	 * @see net.jforum.dao.PollDAO#delete(int)
	 */
	public void delete(int pollId) throws Exception {
		this.deletePollVotes(pollId);
		this.deleteAllPollOptions(pollId);
		this.deletePoll(pollId);
	}
	
	protected void deletePoll(int pollId) throws Exception {
		PreparedStatement poll = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.deletePoll"));

		poll.setInt(1, pollId);
		poll.executeUpdate();
		poll.close();
	}
	
	protected void deletePollVotes(int pollId) throws Exception {
		PreparedStatement poll = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.deletePollVoters"));

		poll.setInt(1, pollId);
		poll.executeUpdate();
		poll.close();
	}
	
	protected void deleteAllPollOptions(int pollId) throws Exception
	{
		PreparedStatement poll = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.deleteAllPollOptions"));

		poll.setInt(1, pollId);
		poll.executeUpdate();
		poll.close();
	}
	
	protected void deletePollOptions(int pollId, List deleted) throws Exception {
		Connection connection = JForumExecutionContext.getConnection();
		
		PreparedStatement options = connection.prepareStatement(SystemGlobals.getSql("PollModel.deletePollOption"));

		for (Iterator iter = deleted.iterator(); iter.hasNext(); ) {
			PollOption o = (PollOption)iter.next();
	
			// Option
			options.setInt(1, pollId);
			options.setInt(2, o.getId());
			options.executeUpdate();
		}
		
		options.close();
	}
	
	protected void updatePollOptions(int pollId, List options) throws Exception
	{
		PreparedStatement p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.updatePollOption"));
		
		for (Iterator iter = options.iterator(); iter.hasNext(); ) {
			PollOption o = (PollOption)iter.next();
			
			p.setString(1, o.getText());
			p.setInt(2, o.getId());
			p.setInt(3, pollId);
			
			p.executeUpdate();
		}
		
		p.close();
	}
	
	/**
	 * @see net.jforum.dao.PollDAO#update(net.jforum.Poll)
	 */
	public void update(Poll poll) throws Exception {
		this.updatePoll(poll);
		
		if (poll.getChanges() != null) {
			this.deletePollOptions(poll.getId(), poll.getChanges().getDeletedOptions());
			this.updatePollOptions(poll.getId(), poll.getChanges().getChangedOptions());
			this.addNewPollOptions(poll.getId(), poll.getChanges().getNewOptions());
		}
	}
	
	protected void updatePoll(Poll poll) throws Exception {
		PreparedStatement p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("PollModel.updatePoll"));

		p.setString(1, poll.getLabel());
		p.setInt(2, poll.getLength());
		p.setInt(3, poll.getId());
		
		p.executeUpdate();
		p.close();
	}
}
