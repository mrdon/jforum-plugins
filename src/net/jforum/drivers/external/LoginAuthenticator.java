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
 *
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.external;

import java.util.Map;

import net.jforum.entities.User;
import net.jforum.model.UserModel;


/**
 * @author Rafael Steil
 * @author Pieter Olivier
 * @version $Id: LoginAuthenticator.java,v 1.2 2005/03/16 16:13:14 rafaelsteil Exp $
 */
public interface LoginAuthenticator 
{
	/**
	 * Authenticates an user.
	 * 
	 * @param username Username
	 * @param password Password
	 * @param extraParams Extra parameters, if any. 
	 * @return An instance of a {@link net.jforum.entities.User} or <code>null</code>
	 * @throws Exception
	 */
	public User validateLogin(String username, String password, Map extraParams) throws Exception;
	
	/**
	 * Sets the user model for the instance
	 * 
	 * @param userModel The user model to set
	 */
	public void setUserModel(UserModel userModel);
}
