package com.example.demo.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JobDescriptor;
import com.example.demo.service.SchedulingService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SchedulingController {
	
	static Logger LOG = LogManager.getLogger(MainController.class);
	
	@Autowired
	SchedulingService schedulingService;
	
	@PostMapping("/groups/{group}/jobs")
	public ResponseEntity<JobDescriptor> createJob(@PathVariable String group, @RequestBody JobDescriptor descriptor) {
		LOG.info("Creating Job"+descriptor.toString());
		return new ResponseEntity<>(schedulingService.createJob(group, descriptor), CREATED);
	}
	
	@GetMapping(path = "/groups/{group}/jobs/{name}")
	public ResponseEntity<JobDescriptor> findJob(@PathVariable String group, @PathVariable String name) {
		return schedulingService.findJob(name, group)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(path = "/groups/{group}/jobs/isRunning/{name}")
	public Boolean jobExist(@PathVariable String group, @PathVariable String name) {
		LOG.info("Finding job "+name);
		return schedulingService.findJob(name, group).isPresent();
	}

	
	@PutMapping(path = "/groups/{group}/jobs/{name}")
	public ResponseEntity<Void> updateJob(@PathVariable String group, @PathVariable String name, @RequestBody JobDescriptor descriptor) {
		schedulingService.updateJob(group, name, descriptor);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/groups/{group}/jobs/{name}")
	public ResponseEntity<Object> deleteJob(@PathVariable String group, @PathVariable String name) {
		LOG.info("Trying to delete job "+name);
		schedulingService.deleteJob(group, name);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(path = "/groups/{group}/jobs/{name}/pause")
	public ResponseEntity<Void> pauseJob(@PathVariable String group, @PathVariable String name) {
		schedulingService.pauseJob(group, name);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(path = "/groups/{group}/jobs/{name}/resume")
	public ResponseEntity<Void> resumeJob(@PathVariable String group, @PathVariable String name) {
		schedulingService.resumeJob(group, name);
		return ResponseEntity.noContent().build();
	}
}
