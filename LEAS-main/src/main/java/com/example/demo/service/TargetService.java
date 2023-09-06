package com.example.demo.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Query;
import com.example.demo.model.Target;
import com.example.demo.repository.TargetRepository;

@Service
public class TargetService {
   
	static Logger LOG = LogManager.getLogger(TargetService.class);

	@Autowired
	private TargetRepository targetRepository;
	
	@Autowired 
	private QueryService queryService;
	
	//Create operation
	public Target create(Target target) {
		LOG.info("Creating a new target {} ",target.getTargetName());
		Target t= targetRepository.save(target);
		if(t==null) {
			LOG.error("Target {} could not be created",target.getTargetName());
		}
		else {
			LOG.info("Target {} created successfully",target.getTargetName());
		}
		return t;
	}
	
	//Retrieve operation
	public List<Target> getAll(){
		LOG.info("Reteiving all target");
		List<Target> targetList= targetRepository.findAll();
		if(targetList==null) {
			LOG.error("Retrived null targets");
		}
		else {
			LOG.info("Retrived targets");
		}
		return targetList;
	}
	
	public Target getByTargetName(String targetName) {
		LOG.info("Retreving target by name");
		return targetRepository.findByTargetName(targetName);
	}
	
	//Update operation
	public Target update(String targetName,Target targetDetails) {
		Target p = targetRepository.findByTargetName(targetName);
		LOG.info("Updating target {}",targetName);
		p.setType(targetDetails.getType());
		p.setTargetName(targetDetails.getTargetName());
		p.setDescription(targetDetails.getDescription());
		p.setFilename(targetDetails.getFilename());
		p.setLocation(targetDetails.getLocation());
		p.setUserId(targetDetails.getUserId());
		p.setPassword(targetDetails.getPassword());
		return targetRepository.save(p);
	}
	//Delete operation
	public void deleteAll() {
		LOG.info("Deleting all target");
		targetRepository.deleteAll();
		
		//delete targets from all queries
		queryService.getAll().forEach(query->{
			ArrayList<String> maskList = query.getMaskList();
			maskList.clear();
			query.setMaskList(maskList);
		});
	}
	
	public void delete(String targetName) {
		Target p = targetRepository.findByTargetName(targetName);
		LOG.info("Deleting target ");
		targetRepository.delete(p);
		
		//Make sure to delete mask from associated queries as well
		List<Query> queryList = queryService.getAll();
		queryList.forEach(query->{
			if(query.getTargetName().compareTo(targetName)==0) {
				query.setTargetName(null);
			}
		});
	}
	
	public long countMask() {
		LOG.info("Retreving count of target");
		return targetRepository.count();
	}
}
