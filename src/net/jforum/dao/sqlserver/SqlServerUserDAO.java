/*
 * Copyright (c) Rafael Steil
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
 * Created on 24/05/2004 22:36:07
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.dao.sqlserver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.jforum.JForumExecutionContext;
import net.jforum.util.preferences.SystemGlobals;
import net.jforum.util.DbUtils;
import org.apache.log4j.Logger;

/**
 * @author Andre de Andrade da Silva - andre.de.andrade@gmail.com
 * @version $Id: SqlServerUserDAO.java,v 1.7 2006/08/20 12:19:08 sergemaslyukov Exp $
 */
public class SqlServerUserDAO extends net.jforum.dao.generic.GenericUserDAO
{
    private final static Logger log = Logger.getLogger(SqlServerUserDAO.class);
    
    /**
	 * @see net.jforum.dao.UserDAO#selectAll(int, int)
	 */
	public List selectAll(int startFrom, int count)
	{
		PreparedStatement p=null;
        ResultSet rs=null;

        try
        {
            if (count > 0) {
                p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("GenericModel.selectByLimit")
                        + " "
                        + count
                        + " "
                        + SystemGlobals.getSql("UserModel.selectAllByLimit"));
                p.setInt(1, startFrom);
                p.setInt(2, count);
            }
            else {
                p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("UserModel.selectAll"));
            }

            rs = p.executeQuery();

            return super.processSelectAll(rs);
        }
        catch (SQLException e) {
            String es = "Erorr selectActiveBannerByPlacement()";
            log.error(es, e);
            throw new RuntimeException(es, e);
        }
        finally {
            DbUtils.close(rs, p);
        }
    }
	
	/** 
	 * @see net.jforum.dao.UserDAO#selectAllWithKarma(int, int)
	 */
	public List selectAllWithKarma(int startFrom, int count)
	{
        try
        {
            return super.loadKarma( this.selectAll(startFrom, count) );
        }
        catch (SQLException e)
        {
            String es = "Erorr selectAllWithKarma()";
            log.error(es, e);
            throw new RuntimeException(es, e);
        }
    }
	
}
