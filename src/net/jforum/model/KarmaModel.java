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
 * Created on Jan 11, 2005 11:00:06 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.model;

import net.jforum.entities.Karma;
import net.jforum.entities.KarmaStatus;

/**
 * @author Rafael Steil
 * @version $Id: KarmaModel.java,v 1.1 2005/01/13 23:30:11 rafaelsteil Exp $
 */
public interface KarmaModel
{
	/**
	 * Insert a new Karma.
	 * 
	 * @param karma The karma to add. The instance should at
	 * least have set the karma status, the user who is receiving
	 * the karma and the user which is setting the karme.
	 * @throws Exception
	 */
	public void addKarma(Karma karma) throws Exception;
	
	/**
	 * Gets the karma status of some user.
	 * 
	 * @param userId The user if to gets the karma status
	 * @return A <code>net.jforum.entities.KarmaStatus</code> instance, or
	 * <code>null</code> if no records were found
	 */
	public KarmaStatus selectKarmaStatus(int userId) throws Exception;
	
	/**
	 * Updates a karma
	 * @param karma The karma instance to update
	 */
	public void update(Karma karma) throws Exception;
}
