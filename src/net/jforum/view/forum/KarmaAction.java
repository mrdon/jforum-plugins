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
 * Created on Jan 11, 2005 11:44:06 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import net.jforum.Command;
import net.jforum.SessionFacade;
import net.jforum.entities.Karma;
import net.jforum.model.DataAccessDriver;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: KarmaAction.java,v 1.1 2005/01/13 23:30:16 rafaelsteil Exp $
 */
public class KarmaAction extends Command
{
	public void positive() throws Exception
	{
		this.addKarma(true);
	}
	
	public void negative() throws Exception
	{
		this.addKarma(false);
	}
	
	private void addKarma(boolean positive) throws Exception
	{
		int toUserId = this.request.getIntParameter("to_user_id");
		int fromUserId = SessionFacade.getUserSession().getUserId();
		
		if (fromUserId == SystemGlobals.getIntValue(ConfigKeys.ANONYMOUS_USER_ID)) {
			this.anonymousIsDenied();
			return;
		}
		
		Karma karma = new Karma();
		karma.setFromUserId(fromUserId);
		karma.setUserId(toUserId);
		karma.setKarmaStatus(positive);
		
		DataAccessDriver.getInstance().newKarmaModel().addKarma(karma);
		
		// TODO: return to the referrer page
	}
	
	public void show() throws Exception
	{
		
	}
	
	public void edit() throws Exception
	{
		
	}
	
	private void anonymousIsDenied()
	{
		// TODO
	}

	/**
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception
	{
		this.context.put("moduleAction", "message.htm");
		this.context.put("message", I18n.getMessage("invalidAction"));
	}

}
