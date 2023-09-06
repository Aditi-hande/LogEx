package com.example.demo.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Mask;
import com.example.demo.service.MaskService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MaskController {

	static Logger LOG = LogManager.getLogger(MaskController.class);

	@Autowired
	private MaskService maskService;
	
	
	@RequestMapping("/mask/create")
	public Mask create(@RequestBody Mask mask) {
		return maskService.create(mask);
	}
	
	@RequestMapping("/mask/{maskName}")
	public Mask getMask(@PathVariable String maskName) {
		return maskService.getByMaskName(maskName);
	}
	@PutMapping("/mask/{maskName}")
	public Mask update(@PathVariable String maskName,@RequestBody Mask maskDetails) {
		LOG.info("Updating mask"+maskName);
		return maskService.update(maskName, maskDetails);
	}

	@RequestMapping("/mask/getAll")
	public List<Mask> getAll(){
		return maskService.getAll();
	}
	
	@DeleteMapping("/mask/{maskName}")
	public void delete(@PathVariable String maskName) {
		maskService.delete(maskName);
	}
	
	@RequestMapping ("/mask/deleteAll")
	public void deleteAll() {
		maskService.deleteAll();
	}
	
	@RequestMapping("/mask/count")
	public long countMask() {
		return maskService.countMask();
	}
	
}
