/*
 * Copyright (c) 2003, 2004 Rafael Steil
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
 * Created on Jan 17, 2005 4:31:45 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.model;

import java.util.List;

import net.jforum.entities.QuotaLimit;

/**
 * @author Rafael Steil
 * @version $Id: AttachmentModel.java,v 1.1 2005/01/17 18:52:31 rafaelsteil Exp $
 */
public interface AttachmentModel
{
	/**
	 * Inserts a new quota limit.
	 * 
	 * @param limit The data to insert
	 * @throws Exception
	 */
	public void addQuotaLimit(QuotaLimit limit) throws Exception;
	
	/**
	 * Updates a quota limit.
	 * 
	 * @param limit The data to update
	 * @throws Exception
	 */
	public void updateQuotaLimit(QuotaLimit limit) throws Exception;
	
	/**
	 * Deletes a quota limit
	 * 
	 * @param id The id of the quota to remove
	 * @throws Exception
	 */
	public void removeQuotaLimit(int id) throws Exception;
	
	/**
	 * Gets all registered quota limits
	 * @return A list instance where each entry is a
	 * {@link net.jforum.entities.QuotaLimit} instance.
	 * @throws Exception
	 */
	public List selectQuotaLimit() throws Exception;
}
