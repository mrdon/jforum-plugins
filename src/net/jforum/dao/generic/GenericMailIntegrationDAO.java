/*
 * Created on 28/08/2006 23:12:09
 */
package net.jforum.dao.generic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.jforum.JForumExecutionContext;
import net.jforum.dao.MailIntegrationDAO;
import net.jforum.entities.MailIntegration;
import net.jforum.exceptions.DatabaseException;
import net.jforum.util.DbUtils;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: GenericMailIntegrationDAO.java,v 1.1 2006/08/29 02:32:32 rafaelsteil Exp $
 */
public class GenericMailIntegrationDAO implements MailIntegrationDAO
{
	public void add(MailIntegration integration)
	{
		// TODO Auto-generated method stub

	}

	public void delete(int forumId)
	{
		// TODO Auto-generated method stub

	}

	public MailIntegration find(int forumId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see net.jforum.dao.MailIntegrationDAO#findAll()
	 */
	public List findAll()
	{
		List l = new ArrayList();
		
		PreparedStatement p = null;
		ResultSet rs = null;
		
		try {
			p = JForumExecutionContext.getConnection().prepareStatement(
					SystemGlobals.getSql("MailIntegration.findAll"));
			rs = p.executeQuery();
			
			while (rs.next()) {
				l.add(this.buildMailIntegration(rs));
			}
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
		}
		finally {
			DbUtils.close(rs, p);
		}
		
		return l;
	}
	
	private MailIntegration buildMailIntegration(ResultSet rs) throws SQLException
	{
		MailIntegration mi = new MailIntegration();
		
		mi.setForumId(rs.getInt("forum_id"));
		mi.setForumEmail(rs.getString("forum_email"));
		mi.setPopHost(rs.getString("pop_host"));
		mi.setPopPassword(rs.getString("pop_password"));
		mi.setPopPort(rs.getInt("pop_port"));
		mi.setPopUsername(rs.getString("pop_username"));
		
		return mi;
	}

	public void update(MailIntegration integration)
	{
		// TODO Auto-generated method stub

	}
}
