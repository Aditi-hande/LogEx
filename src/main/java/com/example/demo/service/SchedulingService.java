package com.example.demo.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.implementation.LogRepositoryImplementation;
import com.example.demo.model.JobDescriptor;
@Service
public class SchedulingService {
	//Logger
	static Logger LOG = LogManager.getLogger(SchedulingService.class);

	
	//Scheduler class instance
	private Scheduler scheduler;

	//Constructor
	@Autowired 
	public SchedulingService(Scheduler scheduler) {
		this.scheduler  =scheduler;
	}
	
	
	//save a new job
	public JobDescriptor createJob(final String group,final JobDescriptor jobDescriptor) {
		LOG.info("JobDescriptor recieved");
		jobDescriptor.setGroup(group);
		final JobDetail jobDetail = jobDescriptor.buildJobDetail();
		Set<Trigger> triggersForJob = jobDescriptor.buildTriggers();
		LOG.info("Saving a new job");
		try {
			scheduler.scheduleJob(jobDetail,triggersForJob,false);
			LOG.info("Job with key - {} saved sucessfully", jobDetail.getKey());
		}catch (SchedulerException se) {
			LOG.error("Could not save job with key - {} due to error - {}", jobDetail.getKey(),se.getLocalizedMessage());
			throw new IllegalArgumentException(se.getLocalizedMessage());
		}
		return jobDescriptor;
	}
	
	//find a existing job
	public Optional<JobDescriptor> findJob(final String name, final String group) {
		try {
			JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(name, group));
			if(Objects.nonNull(jobDetail))
				return Optional.of(
						JobDescriptor.buildDescriptor(jobDetail, 
								scheduler.getTriggersOfJob(JobKey.jobKey(name, group))));
		} catch (SchedulerException e) {
			LOG.error("Could not find job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
		}
		LOG.error("Could not find job with key - {}.{}", group, name);
		return Optional.empty();
	}
		
	//update a job with new details
	public void updateJob(final String name,final String group,final JobDescriptor jobDescriptor) {
		try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(name,group));
            if (jobDetail == null) {
                LOG.error("Failed to find job with ID '{}.{} to update'", group,name);
                return;
            }
            
            jobDetail.getJobDataMap().put("query",jobDescriptor.getQuery());
            
            JobBuilder jobBuilder = jobDetail.getJobBuilder();
            JobDetail newJobDetail = jobBuilder.usingJobData(jobDetail.getJobDataMap()).storeDurably().build();
			scheduler.addJob(newJobDetail, true);
			LOG.info("Updated job with key - {}", newJobDetail.getKey());
			return;
        } catch (final SchedulerException e) {
			LOG.error("Could not find job with key - {}.{} to update due to error - {}", group, name, e.getLocalizedMessage());
        }
	}
	
	public Boolean deleteJob(final String group,final String name) {
		try {
			scheduler.deleteJob(new JobKey(name,group));
			LOG.info("Deleted job with key - {}.{}", group, name);
			return true;
		}catch (SchedulerException se) {
			LOG.error("Could not delete job with key - {}.{} due to error - {}", group, name, se.getLocalizedMessage());
			return false;

		}
	}
	
	public void pauseJob(final String group,final String name) {
		try {
			scheduler.pauseJob(JobKey.jobKey(name, group));
			LOG.info("Paused job with key - {}.{}", group, name);
		} catch (SchedulerException e) {
			LOG.error("Could not pause job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
		}
	}
	
	public void resumeJob(String group, String name) {
		try {
			scheduler.resumeJob(JobKey.jobKey(name, group));
			LOG.info("Resumed job with key - {}.{}", group, name);
		} catch (SchedulerException e) {
			LOG.error("Could not resume job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
		}
	}
	
}
