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
 * This file creation date: 23/04/2004 - 20:24:40
 * The JForum Project
 * http://www.jforum.net
 */ 
package net.jforum.tools.searchIndexer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Rafael Steil
 * @version $Id: SearchIndexer.java,v 1.1 2006/08/20 22:47:52 rafaelsteil Exp $
 */
public class SearchIndexer
{
	private Connection conn;

	private void openConnection(String url) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection(url);
	}
	
	private void closeConnection() throws SQLException
	{
		this.conn.close();
	}
	
	private void makeIndexEx() throws Exception
	{
		this.conn.prepareStatement("DELETE FROM jforum_search_words").executeUpdate();
		this.conn.prepareStatement("DELETE FROM jforum_search_wordmatch").executeUpdate();
		
		PreparedStatement posts = this.conn.prepareStatement("SELECT post_text AS text, post_subject AS subject, post_id AS id FROM jforum_posts_text limit 400");
		PreparedStatement words = this.conn.prepareStatement("INSERT INTO jforum_search_words ( word, word_hash ) VALUES (?, ?)");
		
		int counter = 1;
		String[] parts;

		ResultSet rsPosts = posts.executeQuery();
		Statement match = this.conn.createStatement();
		
		System.out.println("Starting...");

		StringBuffer sb = new StringBuffer(512);

		while (rsPosts.next()) {
			int postId = rsPosts.getInt("id");

			String text = new StringBuffer(rsPosts.getString("text")).append(" ")
				.append(rsPosts.getString("subject")).toString();
			
			if (counter++ % 100 == 0) {
				System.out.println("Indexed " + counter + " posts so far");
			}
			
			parts = text.toLowerCase().replaceAll("[\\.\\\\\\/~^&\\(\\)-_+=!@#$%\"\'\\[\\]\\{\\}?<:>,*\n\r\t]", " ").split(" ");

			List allWords = new ArrayList();

			sb.delete(0, sb.length());
			
			// Go through all words
			for (int i = 0; i < parts.length; i++) {
				if (parts[i].length() < 3) {
					continue;
				}

				String w = parts[i].trim();
				
				if (!allWords.contains(w)) {
					allWords.add(w);
					sb.append('\'').append(w).append('\'').append(",");
				}
			}

			String in = sb.substring(0, sb.length() - 1);
			
			String sql = "SELECT word_id, word FROM jforum_search_words WHERE word IN (" + in + ")";
			
			Statement s = this.conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			List newWords = new ArrayList();
			
			while (rs.next()) {
				newWords.add(rs.getString("word"));
			}
			
			s.close();
			
			allWords.removeAll(newWords);
			
			// Insert the remaining words
			for (Iterator iter = allWords.iterator(); iter.hasNext(); ) {
				String ww = (String)iter.next();
				words.setString(1, ww);
				words.setInt(2, ww.hashCode());
				words.executeUpdate();
			}
			
			sql = "INSERT INTO jforum_search_wordmatch (post_id, word_id, title_match) "
					+ " SELECT " + postId + ", word_id, 0 FROM jforum_search_words WHERE word IN (" + in + ")";
			match.executeUpdate(sql);
			rs.close();
		}
		
		match.close();
		rsPosts.close();
	}
	
	public static void main(String[] args)
	{
		if (args.length != 1) {
			System.out.println("Usage: java net.jforum.tools.searchIndexer.SearchIndexer <mysql connection string>");
			System.out.println("Example: java net.jforum.tools.searchIndexer.SearchIndexer \"jdbc:mysql://localhost/jforum?user=root&password=root\"");
			System.out.println("\nSee http://www.jforum.net/help.htm for more information");
			
			System.exit(1);
		}
		
		SearchIndexer indexer = new SearchIndexer();
		try {
			indexer.openConnection(args[0]);
			indexer.conn.setAutoCommit(false);
			
			indexer.makeIndexEx();
			
			indexer.conn.commit();			
			indexer.closeConnection();
		}
		catch (Exception e) {
			if (indexer.conn != null) {
				try {
					indexer.conn.rollback();
				}
				catch (SQLException se) {
					se.printStackTrace();
				}
			}

			e.printStackTrace();
		}
	}
}