/*
 * Created on 27/06/2005 00:20:38
 */
package net.jforum.view.forum;

import net.jforum.Command;
import net.jforum.JForumExecutionContext;
/**
 * Loads and parse javascript files with FTL statements.
 * 
 * @author Rafael Steil
 * @version $Id: JSAction.java,v 1.4 2006/01/29 15:06:56 rafaelsteil Exp $
 */
public class JSAction extends Command
{
	/**
	 * Loads and parses a javascript file. 
	 * The filename should be into the "js" directory and should
	 * have the extension ".js".
	 * @see net.jforum.Command#list()
	 */
	public void list() throws Exception
	{
		JForumExecutionContext.setContentType("text/javascript");
		
		String filename = this.request.getParameter("js");
		
		this.templateName = "js/" + filename + ".js";
	}
}
