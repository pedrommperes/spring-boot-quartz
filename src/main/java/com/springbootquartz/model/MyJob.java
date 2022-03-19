package com.springbootquartz.model;

import lombok.Data;

@Data
public class MyJob {
	private String triggerName;
	private String cron;
}
