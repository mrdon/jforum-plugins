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
 * This file creation date: 21/05/2004 - 15:33:36
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum.common;

import java.util.Date;
import java.util.List;
import net.jforum.JForum;
import net.jforum.entities.Poll;
import net.jforum.entities.PollOption;

/**
 * @author David Almilli
 * @version $Id: PollCommon.java,v 1.1 2005/11/10 18:30:08 almilli Exp $
 */
public class PollCommon {
	private PollCommon() {}

	public static Poll fillPollFromRequest() throws Exception {
		String label = JForum.getRequest().getParameter("poll_label");
		if (label == null || label.length() == 0) {
			return null;
		}
		Poll poll = new Poll();
		poll.setStartTime(new Date());
		poll.setLabel(label);
		
		int i=1;
		String option;
		do {
			option = JForum.getRequest().getParameter("poll_option_" + i);
			if (option != null) {
				option = option.trim();
			}
			if (option != null && option.length() > 0) {
				PollOption po = new PollOption();
				po.setId(i);
				po.setText(option);
				poll.addOption(po);
			}
			i++;
		} while (option != null);
		
		String pollLength = JForum.getRequest().getParameter("poll_length");
		if (pollLength != null && pollLength.length() > 0) {
			poll.setLength(Integer.parseInt(pollLength));
		}
		return poll;
	}
	
	public static boolean isSamePoll(Poll first, Poll second) {
		if (first == null || second == null) {
			return false;
		}
		boolean isSame = first.getLabel().equals(second.getLabel());
		isSame &= first.getLength() == second.getLength();
		List firstOptions = first.getOptions();
		List secondOptions = second.getOptions();
		if (firstOptions.size() == secondOptions.size()) {
			PollOption firstOption;
			PollOption secondOption;
			for (int i=0; i < firstOptions.size(); i++) {
				firstOption = (PollOption)firstOptions.get(i);
				secondOption = (PollOption)secondOptions.get(i);
				isSame &= firstOption.getText().equals(secondOption.getText());
				isSame &= firstOption.getId() == secondOption.getId();
			}
		} else {
			isSame = false;
		}
		return isSame;
	}
}
