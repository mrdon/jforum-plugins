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
 * Created on Aug 2, 2004 by pieter
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.external;

import java.sql.Connection;

import net.jforum.model.CategoryModel;
import net.jforum.model.ConfigModel;
import net.jforum.model.DataAccessDriver;
import net.jforum.model.ForumModel;
import net.jforum.model.GroupModel;
import net.jforum.model.PostModel;
import net.jforum.model.PrivateMessageModel;
import net.jforum.model.RankingModel;
import net.jforum.model.SearchModel;
import net.jforum.model.SmilieModel;
import net.jforum.model.TopicModel;
import net.jforum.model.TreeGroupModel;
import net.jforum.model.UserModel;
import net.jforum.model.UserSessionModel;
import net.jforum.model.security.GroupSecurityModel;
import net.jforum.model.security.UserSecurityModel;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Pieter Olivier
 * @version $Id: ExternalLoginDataAccessDriver.java,v 1.3 2004/12/29 17:18:43 rafaelsteil Exp $
 */
public class ExternalLoginDataAccessDriver extends DataAccessDriver {
	private DataAccessDriver concreteAccessDriver;
	private UserModel userModel;
	
	public ExternalLoginDataAccessDriver() throws Exception {
		Class cls = Class.forName(SystemGlobals.getValue("dao.driver.concrete"));
		concreteAccessDriver = (DataAccessDriver) cls.newInstance();
		userModel = new ExternalLoginUserModel(concreteAccessDriver.newUserModel());
	}

	public CategoryModel newCategoryModel() {
		return concreteAccessDriver.newCategoryModel();
	}
	
	public CategoryModel newCategoryModel(Connection conn) {
		return concreteAccessDriver.newCategoryModel(conn);
	}

	public ForumModel newForumModel() {
		return concreteAccessDriver.newForumModel();
	}
	
	public ForumModel newForumModel(Connection conn) {
		return concreteAccessDriver.newForumModel(conn);
	}

	public GroupModel newGroupModel() {
		return concreteAccessDriver.newGroupModel();
	}

	public GroupSecurityModel newGroupSecurityModel() {
		return concreteAccessDriver.newGroupSecurityModel();
	}

	public PostModel newPostModel() {
		return concreteAccessDriver.newPostModel();
	}

	public PrivateMessageModel newPrivateMessageModel() {
		return concreteAccessDriver.newPrivateMessageModel();
	}

	public RankingModel newRankingModel() {
		return concreteAccessDriver.newRankingModel();
	}

	public SearchModel newSearchModel() {
		return concreteAccessDriver.newSearchModel();
	}

	public SmilieModel newSmilieModel() {
		return concreteAccessDriver.newSmilieModel();
	}

	public TopicModel newTopicModel() {
		return concreteAccessDriver.newTopicModel();
	}

	public TreeGroupModel newTreeGroupModel() {
		return concreteAccessDriver.newTreeGroupModel();
	}

	public UserModel newUserModel() {
		return userModel;
	}

	public UserSecurityModel newUserSecurityModel() {
		return concreteAccessDriver.newUserSecurityModel();
	}

	public UserSessionModel newUserSessionModel() {
		return concreteAccessDriver.newUserSessionModel();
	}
	
	public ConfigModel newConfigModel() {
		return concreteAccessDriver.newConfigModel();
	}
	
	public ConfigModel newConfigModel(Connection conn) {
		return concreteAccessDriver.newConfigModel(conn);
	}
}