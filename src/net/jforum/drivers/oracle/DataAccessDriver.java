/*
 * Copyright (c) 2003, 2004, Rafael Steil
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
 * This file creation date: 24/05/2004 / 12:01 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.drivers.oracle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.jforum.JForum;
import net.jforum.drivers.oracle.security.GroupSecurityModel;
import net.jforum.drivers.oracle.security.UserSecurityModel;

/**
 * @author Rafael Steil
 * @version $Id: DataAccessDriver.java,v 1.1 2005/01/13 23:30:08 rafaelsteil Exp $
 */
public class DataAccessDriver extends net.jforum.drivers.generic.DataAccessDriver
{
	private static PostModel postModel = new PostModel();
	private static TopicModel topicModel = new TopicModel();
	private static ForumModel forumModel = new ForumModel();
	private static SearchModel searchModel = new SearchModel();
	private static SmilieModel smilieModel = new SmilieModel();
	private static UserModel userModel = new UserModel();
	private static GroupSecurityModel groupSecurityModel = new GroupSecurityModel();
	private static UserSecurityModel userSecurityModel = new UserSecurityModel();
	private static CategoryModel categoryModel = new CategoryModel();
	private static PrivateMessageModel pmModel = new PrivateMessageModel();

    public static String readBlobUTF16BinaryStream(ResultSet rs, String fieldName) throws IOException, SQLException
    {
        Blob clob = rs.getBlob(fieldName);

        InputStream is = clob.getBinaryStream();
        int readedBytes = 0;
        final int bufferSize = 4096;
        StringBuffer sb = new StringBuffer();
        do
        {
            byte[] bytes = new byte[bufferSize];
            readedBytes = is.read(bytes);
            if (readedBytes > 0) {
                String readed =  new String(bytes, 0, readedBytes, "UTF-16");
                sb.append(readed);
            }
        }
        while(readedBytes == bufferSize);

        is.close();

        return sb.toString();
    }
    /**
     * The query should look like:
     *
     * SELECT blob_field from any_table WHERE id = ? FOR UPDATE
     *
     * BUT KEEP IN MIND:
     *
     * When you insert record in previous step, it should go with empty_blob() like:
     *
     * INSERT INTO jforum_posts_text ( post_text ) VALUES (EMPTY_BLOB())
     *
     * @param query
     * @param idForQuery
     * @param value
     * @throws IOException
     * @throws SQLException
     */
    public static void writeBlobUTF16BinaryStream( String query, int idForQuery, String value ) throws IOException, SQLException
    {
        PreparedStatement p = JForum.getConnection().prepareStatement(query);
        p.setInt(1, idForQuery);

        ResultSet rs = p.executeQuery();
        rs.next();
        Blob post_text = rs.getBlob(1);

        OutputStream blobWriter = ((oracle.sql.BLOB)post_text).getBinaryOutputStream();
        blobWriter.write(value.getBytes("UTF16"));

        blobWriter.close();
        rs.close();
        p.close();
    }
	/**
	 * @see net.jforum.model.DataAccessDriver#newPostModel()
	 */
	public net.jforum.model.PostModel newPostModel()
	{
		return postModel;
	}

	/** 
	 * @see net.jforum.model.DataAccessDriver#newTopicModel()
	 */
	public net.jforum.model.TopicModel newTopicModel()
	{
		return topicModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newForumModel()
	 */
	public net.jforum.model.ForumModel newForumModel()
	{
		return forumModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newSearchModel()
	 */
	public net.jforum.model.SearchModel newSearchModel()
	{
		return searchModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newSmilieModel()
	 */
	public net.jforum.model.SmilieModel newSmilieModel()
	{
		return smilieModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newUserModel()
	 */
	public net.jforum.model.UserModel newUserModel()
	{
		return userModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newGroupSecurityModel()
	 */
	public net.jforum.model.security.GroupSecurityModel newGroupSecurityModel()
	{
		return groupSecurityModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newUserSecurityModel()
	 */
	public net.jforum.model.security.UserSecurityModel newUserSecurityModel()
	{
		return userSecurityModel;
	}
	
	/** 
	 * @see net.jforum.model.DataAccessDriver#newCategoryModel()
	 */
	public net.jforum.model.CategoryModel newCategoryModel()
	{
		return categoryModel;
	}
	
	public net.jforum.model.PrivateMessageModel newPrivateMessageModel()
	{
		return pmModel;
	}
}
