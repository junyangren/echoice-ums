package org.echoice.ums.quartz;

import java.util.Calendar;
import java.util.Date;

import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.service.UmsServiceFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

public class QuartzTriggerRunner {
	private static int jobSeq=0;
	public static void runGroupSyncTask(){
		Scheduler scheduler=UmsServiceFactory.getQuartzScheduler();
		synchronized (ConfigConstants.QUARTZ_JOBGRUP_KEY) {
			if(jobSeq>99999999){
				jobSeq=0;
			}
			jobSeq++;
		}
		String jobName="jobName-"+jobSeq;
		JobDetail jobDetail=new JobDetail(jobName,ConfigConstants.QUARTZ_JOBGRUP_KEY,GroupQuartzBean.class);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, 10);
		SimpleTrigger simpleTrigger = new SimpleTrigger(jobName, ConfigConstants.QUARTZ_TRIGGER_KEY);
		simpleTrigger.setStartTime(calendar.getTime());
		simpleTrigger.setRepeatInterval(0);
		simpleTrigger.setRepeatCount(0);
		try {
			scheduler.scheduleJob(jobDetail, simpleTrigger);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
