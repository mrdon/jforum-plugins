/*
 * Created on 29/10/2006 10:21:56
 */
package net.jforum.wiki.plugins;


/**
 * A plugin for displaying tips on the wiki. 
 * 
 * Usage: 
 * <code>
 * [{Tip
 * Text text text
 * }]
 * </code>
 * 
 * @author Rafael Steil
 * @version $Id: Tip.java,v 1.1 2006/10/29 15:36:47 rafaelsteil Exp $
 */
public class Tip extends InformationPlugin {
	public Tip() {
		super("check.gif", "tipMacro");
	}
}
