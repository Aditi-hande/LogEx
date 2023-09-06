package com.example.demo.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Target;
import com.example.demo.service.TargetService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TargetController {

	static Logger LOG = LogManager.getLogger(TargetController.class);

	@Autowired
	private TargetService targetService;
	
	@RequestMapping("/target/create")
	public Target create(@RequestBody Target target) {
		return targetService.create(target);
	}
	
	@GetMapping("/target/{targetName}")
	public Target getTarget(@PathVariable String targetName) {
		return targetService.getByTargetName(targetName);
	}

	@PutMapping(path="/target/{targetName}")
	public Target update(@PathVariable(required = false) String targetName,@RequestBody Target targetDetails) {
		LOG.info("target ="+targetName);
		return targetService.update(targetName, targetDetails);
	}
	
	@RequestMapping("/target/getAll")
	public List<Target> getAll(){
		return targetService.getAll();
	}
	
	@DeleteMapping("/target/{targetName}")
	public void delete(@PathVariable String targetName) {
		targetService.delete(targetName);
	}
	
	@RequestMapping ("/target/deleteAll")
	public String deleteAll() {
		targetService.deleteAll();
		return "Deleted all records";
	}
	
	@RequestMapping("/target/count")
	public long countMask() {
		return targetService.countMask();
	}
	
}
