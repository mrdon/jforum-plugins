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
 * Created on 21.09.2004 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.dao.hsqldb;

import net.jforum.dao.postgresql.PostgresqlDataAccessDriver;

/**
 * @author Marc Wick
 * @version $Id: HsqldbDataAccessDriver.java,v 1.6 2005/07/26 03:05:49 rafaelsteil Exp $
 */
public class HsqldbDataAccessDriver extends PostgresqlDataAccessDriver {

	private static HsqldbPostDAO postDao = new HsqldbPostDAO();
	private static HsqldbUserDAO userDao = new HsqldbUserDAO();
	private static HsqldbTopicDAO topicDao = new HsqldbTopicDAO();

	/**
	 * @see net.jforum.dao.DataAccessDriver#newPostDAO()
	 */
	public net.jforum.dao.PostDAO newPostDAO() {
		return postDao;
	}

	/**
	 * @see net.jforum.dao.DataAccessDriver#newTopicDAO()
	 */
	public net.jforum.dao.TopicDAO newTopicDAO() {
		return topicDao;
	}
	
	/**
	 * @see net.jforum.dao.DataAccessDriver#newUserDAO()
	 */
	public net.jforum.dao.UserDAO newUserDAO() {
		return userDao;
	}
}