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
 * Created on 12/11/2004 21:38:51
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.view.forum;

import java.util.HashMap;
import java.util.Map;

import net.jforum.entities.Forum;
import net.jforum.entities.LastPostInfo;
import junit.framework.TestCase;

/**
 * @author Rafael Steil
 * @version $Id: ForumMiscTest.java,v 1.1 2004/11/13 03:14:04 rafaelsteil Exp $
 */
public class ForumMiscTest extends TestCase 
{
	public void testUnreadForums()
	{
		// Tracking time
		Map tracking = new HashMap();
		tracking.put(new Integer(0), new Long(100)); // unread
		tracking.put(new Integer(1), new Long(200)); // unread
		tracking.put(new Integer(2), new Long(300)); // unread
		tracking.put(new Integer(3), new Long(400)); // unread
		tracking.put(new Integer(4), new Long(900)); // read
		
		long lastVisit = 1000;
		int[] lpiTimes = { 501, 510, 600, 700, 500, 1200 };
		boolean[] unreadStatus = { true, true, true, true, false, true };
		
		Map data = new HashMap();
		for (int i = 0; i < lpiTimes.length; i++) {
			LastPostInfo lpi = new LastPostInfo();
			lpi.setPostTimeMillis(lpiTimes[i]);
			lpi.setTopicId(i);
			data.put("lpi" + i, lpi);
			
			data.put("forum" + i, new Forum());
		}
		
		for (int i = 0; i < lpiTimes.length; i++) {
			Forum f = (Forum)data.get("forum" + i);
			f = ForumCommon.checkUnreadPosts(f, (LastPostInfo)data.get("lpi" + i), tracking, lastVisit);
			
			assertEquals("Wrong read status for forum" + i , unreadStatus[i], f.getUnread());
		}
	}
}
