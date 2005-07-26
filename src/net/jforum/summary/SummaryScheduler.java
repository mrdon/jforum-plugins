/*
 * Created on 06/11/2003
 *
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	Class created on Jul 15, 2005
 */
package net.jforum.summary;

import java.io.IOException;
import java.text.ParseException;

import net.jforum.util.preferences.ConfigKeys;
import net.jforum.util.preferences.SystemGlobals;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Schedule the summaries to be sent to the users.
 * 
 * @see net.jforum.summary.SummaryJob
 * 
 * @author Franklin S. Dattein (<a
 *         href="mailto:franklin@portaljava.com">franklin@portaljava.com</a>)
 * 
 */
public class SummaryScheduler {
	private static Scheduler scheduler;

	private static Logger logger = Logger.getLogger(SummaryScheduler.class);

	private static boolean isStarted = false;

	/**
	 * Starts the summary Job. Conditions to start: Is not started yet and is
	 * enabled on the file SystemGlobasl.properties. The to enable it is
	 * "summary.enabled" (ConfigKeys.SUMMARY_IS_ENABLED).
	 * 
	 * @throws SchedulerException
	 * @throws IOException
	 */
	public static void startJob() throws SchedulerException, IOException {

		boolean isEnabled = new Boolean(SystemGlobals
				.getValue(ConfigKeys.SUMMARY_IS_ENABLED)).booleanValue();
		if (!isStarted && isEnabled) {
			String filename = SystemGlobals
					.getValue(ConfigKeys.SEARCH_INDEXER_QUARTZ_CONFIG);

			SystemGlobals.loadAdditionalDefaults(filename);

			String cronExpression = SystemGlobals
					.getValue("org.quartz.context.summary.cron.expression");
			scheduler = new StdSchedulerFactory(filename).getScheduler();
			Trigger trigger = null;
			try {
				trigger = new CronTrigger(SummaryJob.class.getName(),
						"summaryJob", cronExpression);
				logger.info("Starting quartz summary expression "
						+ cronExpression);
				scheduler.scheduleJob(new JobDetail(SummaryJob.class.getName(),
						"summaryJob", SummaryJob.class), trigger);
				scheduler.start();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		isStarted = true;
	}
}
