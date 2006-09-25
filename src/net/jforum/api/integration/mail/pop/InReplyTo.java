/*
 * Created on 24/09/2006 23:04:29
 */
package net.jforum.api.integration.mail.pop;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rafael Steil
 * @version $Id: InReplyTo.java,v 1.1 2006/09/25 02:16:38 rafaelsteil Exp $
 */
public class InReplyTo
{
	private static Random random = new Random(System.currentTimeMillis());
	private int topicId;
	
	public int getTopicId()
	{
		return this.topicId;
	}
	
	public static String build(int topicId, int forumId)
	{
		return new StringBuffer()
			.append(topicId)
			.append('.')
			.append(forumId)
			.append("@jforum")
			.append(random.nextInt(999999))
			.toString();
	}
	
	public static InReplyTo parse(String header)
	{
		InReplyTo irt = new InReplyTo();
		
		if (header != null) {
			// <topicId.forumId@host>
			Matcher matcher = Pattern.compile("(?i)<(.*)\\.(.*)@").matcher(header);
			
			if (matcher.matches()) {
				String s = matcher.group(1);
				
				try {
					irt.topicId = Integer.parseInt(s);
				}
				catch (Exception e) { }
			}
		}
		
		return irt;
	}
}
