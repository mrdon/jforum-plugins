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
 * Created on Dec 29, 2004 1:29:52 PM
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.dao.generic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.jforum.JForumExecutionContext;
import net.jforum.entities.Config;
import net.jforum.exceptions.DatabaseException;
import net.jforum.util.DbUtils;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.Logger;

/**
 * @author Rafael Steil
 * @version $Id: GenericConfigDAO.java,v 1.7 2006/08/20 22:47:28 rafaelsteil Exp $
 */
public class GenericConfigDAO implements net.jforum.dao.ConfigDAO
{
    private final static Logger log = Logger.getLogger(GenericConfigDAO.class);

	/**
	 * @see net.jforum.dao.ConfigDAO#insert(net.jforum.entities.Config)
	 */
	public void insert(Config config)
	{
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("ConfigModel.insert"));
            p.setString(1, config.getName());
            p.setString(2, config.getValue());
            p.executeUpdate();
        }
        catch (SQLException e) {
            String es = "Error insert()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close(p);
        }
    }

	/**
	 * @see net.jforum.dao.ConfigDAO#update(net.jforum.entities.Config)
	 */
	public void update(Config config)
	{
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("ConfigModel.update"));
            p.setString(1, config.getValue());
            p.setString(2, config.getName());
            p.executeUpdate();
        }
        catch (SQLException e) {
            String es = "Error update()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close(p);
        }
    }

	/**
	 * @see net.jforum.dao.ConfigDAO#delete(net.jforum.entities.Config)
	 */
	public void delete(Config config)
	{
		PreparedStatement p=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("ConfigModel.delete"));
            p.setInt(1, config.getId());
            p.executeUpdate();
        }
        catch (SQLException e) {
            String es = "Error delete()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close(p);
        }
	}

	/**
	 * @see net.jforum.dao.ConfigDAO#selectAll()
	 */
	public List selectAll()
	{
		List l = new ArrayList();
		
		PreparedStatement p=null;
        ResultSet rs=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("ConfigModel.selectAll"));
            rs = p.executeQuery();
            while (rs.next()) {
                l.add(this.makeConfig(rs));
            }

            return l;
        }
        catch (SQLException e) {
            String es = "Error selectAll()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close(rs, p);
        }
    }

	/**
	 * @see net.jforum.dao.ConfigDAO#selectByName(java.lang.String)
	 */
	public Config selectByName(String name)
	{
		PreparedStatement p=null;
        ResultSet rs=null;
        try
        {
            p = JForumExecutionContext.getConnection().prepareStatement(SystemGlobals.getSql("ConfigModel.selectByName"));
            p.setString(1, name);
            rs = p.executeQuery();
            Config c = null;

            if (rs.next()) {
                c = this.makeConfig(rs);
            }

            return c;
        }
        catch (SQLException e) {
            String es = "Error selectByName()";
            log.error(es, e);
            throw new DatabaseException(es, e);
        }
        finally {
            DbUtils.close(rs, p);
        }
    }
	
	protected Config makeConfig(ResultSet rs) throws SQLException
	{
		Config c = new Config();
		c.setId(rs.getInt("config_id"));
		c.setName(rs.getString("config_name"));
		c.setValue(rs.getString("config_value"));
		
		return c;
	}
}
