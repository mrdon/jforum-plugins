/*
 * Created on Sep 2, 2004
 *
 */
package net.jforum.view.forum;

import java.util.Comparator;

import net.jforum.entities.Forum;
/**
 * @author James Yong Boon Leong
 *
 */
public class ForumOrderComparator implements Comparator{
	
	public final int compare (Object a, Object b)
	{
		if (((Forum)a).getOrder() > ((Forum)b).getOrder() )
		{
			return 1;
		}
		else if (((Forum)a).getOrder() < ((Forum)b).getOrder() )
		{
			return -1;
		}
		else
		{
			return 0;
		}

	} // end compare
} // end class StringComparator

