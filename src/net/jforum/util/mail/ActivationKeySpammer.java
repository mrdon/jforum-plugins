package net.jforum.util.mail;

import java.util.ArrayList;

import freemarker.template.SimpleHash;
import net.jforum.util.I18n;
import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

public class ActivationKeySpammer extends Spammer
{
	public ActivationKeySpammer(int userId, String username, String email, String hash)
	{
		//gets the url to the forum.
		String forumLink = SystemGlobals.getValue(ConfigKeys.FORUM_LINK);
		if (!forumLink.endsWith("/")) {
			forumLink += "/";
		}

		String url =  forumLink + "user/activateAccount/"+ hash + "/" + userId + SystemGlobals.getValue(ConfigKeys.SERVLET_EXTENSION);
		SimpleHash params = new SimpleHash();
		params.put("url", url);
		params.put("username", username);

		ArrayList recipients = new ArrayList();
		recipients.add(email);

		super.prepareMessage(recipients, params, I18n.getMessage("User.ActivateAccount.mailTitle"), 
				SystemGlobals.getValue(ConfigKeys.MAIL_ACTIVATION_KEY_MESSAGE_FILE));
	}
}
