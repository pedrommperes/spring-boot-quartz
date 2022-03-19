package com.springbootquartz.service;

import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerKey.triggerKey;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootquartz.model.MyJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JobsService {
	@Autowired
	private Scheduler scheduler;
	
	public void executeJob(JobExecutionContext context) {
		log.info("The job " + context.getJobDetail().getKey().getName() +" has begun... [" + context.getFireTime() + "]");

		
		log.info("job " + context.getJobDetail().getKey().getName() +" has finished... [" + context.getNextFireTime() + "]");
	}

	public void rescheduleJob(MyJob myJob) throws SchedulerException {
		if (myJob.getTriggerName() != null || myJob.getCron() != null) {
			if (org.quartz.CronExpression.isValidExpression(myJob.getCron())) {
				Trigger oldTrigger = scheduler.getTrigger(triggerKey(myJob.getTriggerName(), "DEFAULT"));

				// obtain a builder that would produce the trigger
				@SuppressWarnings("rawtypes")
				TriggerBuilder tb = oldTrigger.getTriggerBuilder();

				@SuppressWarnings("unchecked")
				Trigger newTrigger = tb.withSchedule(CronScheduleBuilder.cronSchedule(myJob.getCron()))
						.build();

				scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
			}
		}
	}

	public void pauseJob(String jobName) throws SchedulerException {
		scheduler.pauseJob(jobKey(jobName, "DEFAULT"));
	}

	public void resumeJob(String jobName) throws SchedulerException {
		scheduler.resumeJob(jobKey(jobName, "DEFAULT"));
	}
}
