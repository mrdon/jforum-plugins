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
 * This file creation date: May 11, 2003 / 11:30:45 AM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum;

import java.sql.Connection;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.jforum.entities.UserSession;
import net.jforum.model.DataAccessDriver;
import net.jforum.repository.SecurityRepository;
import net.jforum.util.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: ForumSessionListener.java,v 1.3 2004/05/31 01:58:46 rafaelsteil Exp $
 */
public class ForumSessionListener implements HttpSessionListener 
{
	/** 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) 
	{
		System.out.println("Creating a session: "+ event.getSession().getId());
	} 

	/** 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) 
	{
		UserSession us = SessionFacade.getUserSession(event.getSession().getId());
		if (us != null) {
			Connection conn = null;
			
			try {
				if (us.getUserId() != Integer.parseInt((String)SystemGlobals.getValue("anonymousUserId"))) {
					conn = ConnectionPool.getPool().getConnection();
					DataAccessDriver.getInstance().newUserSessionModel().update(us, conn);
				}
				
				SecurityRepository.remove(us.getUserId());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally  {
				if (conn != null) {
					try {
						ConnectionPool.getPool().releaseConnection(conn);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		SessionFacade.remove(event.getSession().getId());
	}
}
