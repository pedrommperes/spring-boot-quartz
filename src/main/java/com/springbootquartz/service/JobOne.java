package com.springbootquartz.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class JobOne implements Job {
	@Autowired
	JobsService jobsService;
	
	@Override
	public void execute(JobExecutionContext context) {
		jobsService.executeJob(context);
	}
}