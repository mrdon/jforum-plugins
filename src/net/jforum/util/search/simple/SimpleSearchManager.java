/*
 * Copyright (c) JForum Team
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
 * Created on Mar 11, 2005 11:45:30 AM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.search.simple;

import java.util.List;

import net.jforum.JForumExecutionContext;
import net.jforum.dao.DataAccessDriver;
import net.jforum.dao.SearchArgs;
import net.jforum.dao.SearchIndexerDAO;
import net.jforum.entities.Post;
import net.jforum.exceptions.SearchException;
import net.jforum.util.concurrent.Executor;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.search.SearchManager;

/**
 * @author Rafael Steil
 * @version $Id: SimpleSearchManager.java,v 1.10 2007/07/24 15:55:53 rafaelsteil Exp $
 */
public class SimpleSearchManager implements SearchManager
{
	/**
	 * @see net.jforum.util.search.SearchManager#init()
	 */
	public void init() {}

	/**
	 * @see net.jforum.util.search.SearchManager#index(net.jforum.entities.Post)
	 */
	public void index(Post post)
	{
		if (SystemGlobals.getBoolValue(ConfigKeys.BACKGROUND_TASKS)) {
				Executor.execute(new MessageIndexerTask(post));
		}
		else {
			try {
				SearchIndexerDAO indexer = DataAccessDriver.getInstance().newSearchIndexerDAO();
				indexer.setConnection(JForumExecutionContext.getConnection());
				indexer.insertSearchWords(post);
			}
			catch (Exception e) {
				throw new SearchException("Error while indexing a message" + e, e);
			}
		}
	}
	
	/**
	 * @see net.jforum.util.search.SearchManager#search(net.jforum.dao.SearchArgs)
	 */
	public List search(SearchArgs args)
	{
		return DataAccessDriver.getInstance().newSearchDAO().search(args);
	}
}
