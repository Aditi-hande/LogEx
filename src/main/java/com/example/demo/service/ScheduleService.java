package com.example.demo.service;

import java.util.ArrayList;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.JobDescriptor;
import com.example.demo.model.Query;
import com.example.demo.model.Schedule;
import com.example.demo.model.TriggerDescriptor;
import com.example.demo.repository.ScheduleRepository;

@Service
public class ScheduleService {
	
	static Logger LOG = LogManager.getLogger(ScheduleService.class);

	
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private QueryService queryService;
	@Autowired
	private SchedulingService schedulingService;

	public Schedule create(@RequestBody Schedule Schedule) {
		LOG.info("Saving schedule ");
		return scheduleRepository.save(Schedule);
	}
	
	//Retrieve operation
	public List<Schedule> getAll(){
		List<Schedule> scheduleList = scheduleRepository.findAll();
		LOG.info("Retriving all schedules ");
		scheduleList.forEach(x->LOG.info("Schedule = "+x.toString()));
		return scheduleList;
	}
	
	public Schedule getByscheduleName(String scheduleName) {
		LOG.info("Searching a schedule by name");
		return scheduleRepository.findByName(scheduleName);
	}
	
	//Update operation
	public Schedule update(String scheduleName,Schedule scheduleDetails) {
		Schedule s = scheduleRepository.findByName(scheduleName);
		LOG.info("Updating Schedule ");
		s.setQueryList(scheduleDetails.getQueryList());
		s.setCron(scheduleDetails.getCron());
		LOG.info("Updated Schedule ");
		//If any jobs are currently running in RAM update those jobs
		if(schedulingService.findJob(s.getGroup(), s.getGroup()).isPresent()){
			
			//If no queries are present remove job from RAM
			if(s.getQueryList()==null || s.getQueryList().size()==0) {
				LOG.info("Deleting job due to empty queries");
				schedulingService.deleteJob(s.getGroup(), s.getGroup());
			}
			// Else update job running in RAM
			else {
				TriggerDescriptor triggerDescriptor=new TriggerDescriptor(s.getName(),s.getGroup(),s.getCron());
				List<TriggerDescriptor> triggerDescriptors = new ArrayList<TriggerDescriptor>();
				JobDescriptor jobDescriptor = new JobDescriptor(s.getName(), s.getGroup(), s.getQueryList(), triggerDescriptors);
				schedulingService.updateJob(s.getName(), s.getGroup(),jobDescriptor);		
			}
		}
		return scheduleRepository.save(s);
	}
	//Delete operation
	public void deleteAll() {
		LOG.info("Deleting all schedules");
		scheduleRepository.deleteAll();
	}
	
	public void delete(String scheduleName) {
		Schedule p = scheduleRepository.findByName(scheduleName);
		if(p!=null) {
			LOG.info("Deleting schedule");
			if(p.getQueryList()!=null)
			{
				p.getQueryList().forEach(x->{
					Query q  = queryService.getByQueryName(x);
					q.setScheduleName(null);
					queryService.update(x, q);
				});
			}
			if(schedulingService.findJob(p.getName(), p.getGroup()).isPresent()){
				LOG.info("Deleting job");
				schedulingService.deleteJob(p.getGroup(), p.getName());
			}
			scheduleRepository.delete(p);
			LOG.info("Deleted schedule successfully");
		}
		else {
			LOG.info("No such schedule to delete");
		}
	}
	
	public long count() {
		LOG.info("Retriving a count of all schedules");
		return this.scheduleRepository.count();
	}
	
	
}
