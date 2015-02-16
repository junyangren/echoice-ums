package org.echoice.ums.quartz;

import org.echoice.ums.service.UmsServiceFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GroupQuartzBean implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		UmsServiceFactory.getUmsCommonService().commitSyncGroup();
	}

}
