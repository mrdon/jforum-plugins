package net.jforum.util.legacy.clickstream;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.jforum.util.legacy.clickstream.config.ConfigLoader;

/**
 * Determines if a request is actually a bot or spider.
 * 
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody</a>
 * @author Rafael Steil (little hacks for JForum)
 * @version $Id: BotChecker.java,v 1.1 2005/06/14 16:55:21 rafaelsteil Exp $
 */
public class BotChecker
{
	public static boolean isBot(HttpServletRequest request)
	{
		if (request.getRequestURI().indexOf("robots.txt") != -1) {
			// there is a specific request for the robots.txt file, so we assume
			// it must be a robot (only robots request robots.txt)
			return true;
		}
		
		String userAgent = request.getHeader("User-Agent");
		
		if (userAgent != null) {
			List agents = ConfigLoader.instance().getConfig().getBotAgents();
			
			userAgent = userAgent.toLowerCase();
			
			for (Iterator iterator = agents.iterator(); iterator.hasNext(); ) {
				String agent = (String) iterator.next();
				
				if (userAgent.indexOf(agent) != -1) {
					return true;
				}
			}
		}
		
		String remoteHost = request.getRemoteHost(); // requires a DNS lookup
		
		if (remoteHost != null && remoteHost.length() > 0 && remoteHost.charAt(remoteHost.length() - 1) > 64) {
			List hosts = ConfigLoader.instance().getConfig().getBotHosts();
			
			remoteHost = remoteHost.toLowerCase();
			
			for (Iterator iterator = hosts.iterator(); iterator.hasNext(); ) {
				String host = (String) iterator.next();
				
				if (remoteHost.indexOf(host) != -1) {
					return true;
				}
			}
		}

		return false;
	}
}