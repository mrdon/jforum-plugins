/*
 * Created on 24/09/2006 23:04:29
 */
package net.jforum.api.integration.mail.pop;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the In-Reply-To mail header, as well utility methods.
 * 
 * @author Rafael Steil
 * @version $Id: InReplyTo.java,v 1.2 2006/09/25 02:37:07 rafaelsteil Exp $
 */
public class InReplyTo
{
	private static Random random = new Random(System.currentTimeMillis());
	private int topicId;
	
	/**
	 * Returns the topic id this header holds
	 * @return
	 */
	public int getTopicId()
	{
		return this.topicId;
	}
	
	/**
	 * Constructs an In-Reply-To header.
	 * The form is "<topicId.forumId@jforum[some random number]>"
	 * @param topicId
	 * @param forumId
	 * @return
	 */
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
	
	/**
	 * Parses the header, extracting the information it holds
	 * @param header the header's contents to parse
	 * @return the header information parsed
	 */
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
