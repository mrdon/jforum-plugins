/*
 * Created on 04/09/2006 21:23:22
 */
package net.jforum.api.user;

import java.util.List;

import net.jforum.Command;
import net.jforum.dao.DataAccessDriver;
import net.jforum.dao.UserDAO;
import net.jforum.entities.User;
import net.jforum.exceptions.APIException;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: UserAPI.java,v 1.1 2006/09/05 00:53:28 rafaelsteil Exp $
 */
public class UserAPI extends Command
{
	/**
	 * List all users
	 */
	public void list()
	{
		try {
			UserDAO dao = DataAccessDriver.getInstance().newUserDAO();
			List users = dao.selectAll();
		
			this.setTemplateName("api/users.xml");
			this.context.put("users", users);
		}
		catch (Exception e) {
			this.setTemplateName("api/response_message.xml");
			this.context.put("exception", e);
		}
	}
	
	public void insert()
	{
		try {
			String username = this.requiredRequestParameter("username");
			String email = this.requiredRequestParameter("email");
			String password = this.requiredRequestParameter("password");
			
			if (username.length() > SystemGlobals.getIntValue(ConfigKeys.USERNAME_MAX_LENGTH)) {
				throw new APIException(I18n.getMessage("User.usernameTooBig"));
			}
			
			if (username.indexOf('<') > -1 || username.indexOf('>') > -1) {
				throw new APIException(I18n.getMessage("User.usernameInvalidChars"));
			}
			
			UserDAO dao = DataAccessDriver.getInstance().newUserDAO();

			if (dao.isUsernameRegistered(username)) {
				throw new APIException(I18n.getMessage("UsernameExists"));
			}
			
			if (dao.findByEmail(email) != null) {
				throw new APIException(I18n.getMessage("User.emailExists", new Object[] { email }));
			}
			
			// Ok, time to insert the user
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			
			int userId = dao.addNew(user);
			
			this.setTemplateName("api/user_added.xml");
			this.context.put("userId", new Integer(userId));
		}
		catch (Exception e) {
			this.setTemplateName("api/response_message.xml");
			this.context.put("exception", e);
		}
	}
	
	private String requiredRequestParameter(String paramName)
	{
		String value = this.request.getParameter(paramName);
		
		if (value == null || value.trim().length() == 0) {
			throw new APIException("The parameter '" + paramName + "' was not found");
		}
		
		return value;
	}
}
