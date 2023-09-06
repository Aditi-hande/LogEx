package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Schedule;
import com.example.demo.service.ScheduleService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	@RequestMapping("/schedules/create")
	public Schedule create(@RequestBody Schedule Schedule) {
		return scheduleService.create(Schedule);
	}
	
	//Retrieve operation
	@RequestMapping("/schedules/getAll")
	public List<Schedule> getAll(){
		return scheduleService.getAll();
	}
	
	@RequestMapping("/schedules/{scheduleName}")
	public Schedule getByscheduleName(@PathVariable String scheduleName) {
		return scheduleService.getByscheduleName(scheduleName);
	}
	
	@PutMapping("/schedules/{scheduleName}")
	public Schedule update(@PathVariable String scheduleName,@RequestBody Schedule scheduleDetails) {
		return scheduleService.update(scheduleName, scheduleDetails);
	}
	public void deleteAll() {
		scheduleService.deleteAll();
	}
	
	@DeleteMapping("/schedules/{scheduleName}")
	public void delete(@PathVariable String scheduleName) {
		scheduleService.delete(scheduleName);
	}
	
	@RequestMapping("/schedules/count")
	public long count() {
		return scheduleService.count();
	}
}
