package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import com.example.demo.jobs.basicJob;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDescriptor {

	private String name;
	private String group;
	private HashSet<String> query = new HashSet<>();
	@JsonProperty("triggers")
	private List<TriggerDescriptor> triggerDescriptors = new ArrayList<>();
	

	public JobDescriptor(String name,
			String group,
			HashSet<String> query,
			List<TriggerDescriptor> triggerDescriptors) {
		this.name = name;
		this.group = group;
		this.query = query;
		this.triggerDescriptors = triggerDescriptors;
	}
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public HashSet<String> getQuery() {
		return query;
	}

	public void setQuery(HashSet<String> query) {
		this.query = query;
	}

	public List<TriggerDescriptor> getTriggerDescriptors() {
		return triggerDescriptors;
	}

	public void setTriggerDescriptors(List<TriggerDescriptor> triggerDescriptors) {
		this.triggerDescriptors = triggerDescriptors;
	}

	/**
	 * Convenience method for building Triggers of Job
	 * 
	 * @return Triggers for this JobDetail
	 */
	@JsonIgnore
	public Set<Trigger> buildTriggers() {
		Set<Trigger> triggers = new LinkedHashSet<>();
		for (TriggerDescriptor triggerDescriptor : triggerDescriptors) {
			triggers.add(triggerDescriptor.buildTrigger());
		}

		return triggers;
	}


	@Override
	public String toString() {
		return "JobDescriptor [name=" + name + ", group=" + group + ", query=" + query + ", triggerDescriptors="
				+ triggerDescriptors + "]";
	}



	/**
	 * Convenience method that builds a JobDetail
	 * 
	 * @return the JobDetail built from this descriptor
	 */
	public JobDetail buildJobDetail() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("query", query);
		return JobBuilder
				.newJob(basicJob.class)
                .withIdentity(getName(), getGroup())
                .usingJobData(jobDataMap)
                .build();
	}

	/**
	 * Convenience method that builds a descriptor from JobDetail and Trigger(s)
	 * 
	 * @param jobDetail
	 *            the JobDetail instance
	 * @param triggersOfJob
	 *            the Trigger(s) to associate with the Job
	 * @return the JobDescriptor
	 */
	public static JobDescriptor buildDescriptor(JobDetail jobDetail, List<? extends Trigger> triggersOfJob) {
		List<TriggerDescriptor> triggerDescriptors = new ArrayList<>();
		HashSet<String> queryList =(HashSet<String>)jobDetail.getJobDataMap().get("query");
		
		for (Trigger trigger : triggersOfJob) {
		    triggerDescriptors.add(TriggerDescriptor.buildDescriptor(trigger));
		}
		return new JobDescriptor(
				jobDetail.getKey().getName(),
				jobDetail.getKey().getGroup(),
				queryList,
				triggerDescriptors
				);
	}

	
}
