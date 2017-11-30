package com.qlqn.quartz.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlqn.bean.ScheduleJob;



/**
 * 
 * @Description: 计划任务执行处 无状态
 * @author chenjianlin
 * @date 2014年4月24日 下午5:05:47
 */
public class QuartzJobFactory implements Job {

	private static final Logger log = LoggerFactory.getLogger(QuartzJobFactory.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		TaskUtils.invokMethod(scheduleJob);
	}
}