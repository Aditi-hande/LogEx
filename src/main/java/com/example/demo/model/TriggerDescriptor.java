package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;
import static java.time.ZoneId.systemDefault;
import java.util.UUID;
import static org.quartz.CronExpression.isValidExpression;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import org.quartz.JobDataMap;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class TriggerDescriptor {
	private String name;
	private String group;
	private LocalDateTime fireTime;
	private String cron;

	public TriggerDescriptor(String name2, String group2, String cron2) {
		this.name=  name2;
		this.group=group2;
		this.cron = cron2;
	}

	public TriggerDescriptor() {
	}

	public TriggerDescriptor setName(final String name) {
		this.name = name;
		return this;
	}

	public TriggerDescriptor setGroup(final String group) {
		this.group = group;
		return this;
	}

	public TriggerDescriptor setFireTime(final LocalDateTime fireTime) {
		this.fireTime = fireTime;
		return this;
	}

	public TriggerDescriptor setCron(final String cron) {
		this.cron = cron;
		return this;
	}

	private String buildName() {
		return name.isEmpty() ? UUID.randomUUID().toString() : name;
	}

	/**
	 * Convenience method for building a Trigger
	 * 
	 * @return the Trigger associated with this descriptor
	 */
	
	public Trigger buildTrigger() {

		
		if (!cron.isEmpty()) {
			if (!isValidExpression(cron))
				throw new IllegalArgumentException("Provided expression " + cron + " is not a valid cron expression");
			return 	TriggerBuilder
					.newTrigger()
					.withIdentity(buildName(), group)
					.withSchedule(cronSchedule(cron)
							.withMisfireHandlingInstructionFireAndProceed()
							.inTimeZone(TimeZone.getTimeZone(systemDefault())))
					.usingJobData("cron", cron)
					.build();
		} else if (!fireTime.toString().isEmpty()) {
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("fireTime", fireTime);
			return TriggerBuilder
					.newTrigger()
					.withIdentity(buildName(), group)
					.withSchedule(simpleSchedule()
							.withMisfireHandlingInstructionNextWithExistingCount())
					.startAt(Date.from(fireTime.atZone(systemDefault()).toInstant()))
					.usingJobData(jobDataMap)
					.build();
		}
		// @formatter:on
		throw new IllegalStateException("unsupported trigger descriptor " + this);
	}

	/**
	 * 
	 * @param trigger
	 *            the Trigger used to build this descriptor
	 * @return the TriggerDescriptor
	 */
	public static TriggerDescriptor buildDescriptor(Trigger trigger) {
		return new TriggerDescriptor()
				.setName(trigger.getKey().getName())
				.setGroup(trigger.getKey().getGroup())
				.setFireTime((LocalDateTime) trigger.getJobDataMap().get("fireTime"))
				.setCron(trigger.getJobDataMap().getString("cron"));
	}
	

}
