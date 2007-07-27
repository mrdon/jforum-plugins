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
 * Created on 18/07/2007 22:05:37
 * 
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.search;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.jforum.dao.SearchArgs;
import net.jforum.exceptions.SearchException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

/**
 * @author Rafael Steil
 * @version $Id: LuceneSearch.java,v 1.23 2007/07/27 18:39:48 rafaelsteil Exp $
 */
public class LuceneSearch implements NewDocumentAdded
{
	private static final Logger logger = Logger.getLogger(LuceneSearch.class);
	
	private IndexSearcher search;
	private LuceneSettings settings;
	private LuceneResultCollector contentCollector;
	private LuceneResultCollector newMessagesCollector;
	
	public LuceneSearch(LuceneSettings settings, 
		LuceneResultCollector contentCollector, 
		LuceneResultCollector newMessagesCollector)
	{
		this.settings = settings;
		this.contentCollector = contentCollector;
		this.newMessagesCollector = newMessagesCollector;
		
		this.openSearch();
	}
	
	/**
	 * @see net.jforum.search.NewDocumentAdded#newDocumentAdded()
	 */
	public void newDocumentAdded()
	{
		try {
			this.search.close();
			this.openSearch();
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}
	
	/**
	 * @see net.jforum.dao.SearchDAO#search(net.jforum.dao.SearchArgs)
	 */
	public List search(SearchArgs args)
	{
		return this.performSearch(args, this.contentCollector);
	}
	
	public List newMessages(SearchArgs args)
	{
		return this.performSearch(args, this.newMessagesCollector);
	}

	private List performSearch(SearchArgs args, LuceneResultCollector resultCollector)
	{
		List l = new ArrayList();
		
		try {
			StringBuffer criteria = new StringBuffer(256);
			
			this.filterByForum(args, criteria);
			this.filterByKeywords(args, criteria);
			this.filterByDateRange(args, criteria);
			
			Query query = new QueryParser("", new StandardAnalyzer()).parse(criteria.toString());
			
			if (logger.isDebugEnabled()) {
				logger.debug("Generated query: " + query);
			}
			
			Hits hits = this.search.search(query, Sort.RELEVANCE);

			if (hits != null && hits.length() > 0) {
				l = resultCollector.collect(hits, query);
			}
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
		
		return l;
	}
	
	private void filterByDateRange(SearchArgs args, StringBuffer criteria)
	{
		if (args.getTime() != null) {
			criteria.append('(')
			.append(SearchFields.Keyword.DATE)
			.append(": [")
			.append(this.settings.formatDateTime(args.getTime()))
			.append(" TO ")
			.append(this.settings.formatDateTime(new Date()))
			.append(']')
			.append(')');
		}
	}
	
	private String formatDateTime(Date date)
	{
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}

	private void filterByKeywords(SearchArgs args, StringBuffer criteria)
	{
		String[] keywords = args.getKeywords();
		
		for (int i = 0; i < keywords.length; i++) {
			if (args.shouldMatchAllKeywords()) {
				criteria.append(" +");
			}
			
			criteria.append('(')
			.append(SearchFields.Indexed.CONTENTS)
			.append(':')
			.append(keywords[i])
			.append(") ");
		}
	}

	private void filterByForum(SearchArgs args, StringBuffer criteria)
	{
		// TODO: Remove those forums that the current user doesn't
		// have permissions to access
		if (args.getForumId() > 0) {
			criteria.append("+(")
				.append(SearchFields.Keyword.FORUM_ID)
				.append(':')
				.append(args.getForumId())
				.append(") ");
		}
	}
	
	private void openSearch()
	{
		try {
			this.search = new IndexSearcher(this.settings.directory());
		}
		catch (IOException e) {
			throw new SearchException(e.toString(), e);
		}
	}
}
