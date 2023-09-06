package com.example.demo.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Mask;
import com.example.demo.model.Query;
import com.example.demo.repository.MaskRepository;

@Service
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MaskService {
   
	static Logger LOG = LogManager.getLogger(MaskService.class);

	@Autowired
	private MaskRepository maskRepository;
	
	@Autowired 
	private QueryService queryService;
	
	public Mask create(Mask mask) {
		LOG.info("Creating a mask");
		return maskRepository.save(mask);
	}
	
	//Retrieve operation
	public List<Mask> getAll(){
		LOG.info("Retreiving all masks");
		return maskRepository.findAll();
	}
	
	public Mask getByMaskName(String maskName) {
		LOG.info("Searching for a mask by name");
		return maskRepository.findByMaskName(maskName);
	}
	
	//Update operation
	public Mask update(String maskName,Mask maskDetails) {
		LOG.info("Updating a mask");
		Mask p = maskRepository.findByMaskName(maskName);
		p.setMaskName(maskDetails.getMaskName());
		p.setRegex(maskDetails.getRegex());
		p.setReplaceString(maskDetails.getReplaceString());
		p.setDescription(maskDetails.getDescription());
		return maskRepository.save(p);
	}
	//Delete operation
	public void deleteAll() {
		LOG.info("Deleting all masks");
		maskRepository.deleteAll();
		
		//Delete associated mask from all queries
		queryService.getAll().forEach(query->{
			ArrayList<String> maskList = query.getMaskList();
			maskList.clear();
			query.setMaskList(maskList);
		});
		
	}
	
	public void delete(String maskName) {
		LOG.info("Deleting a mask");
		Mask p = maskRepository.findByMaskName(maskName);
		maskRepository.delete(p);
		
		//Make sure to delete mask from associated queries as well
		List<Query> queryList = queryService.getAll();
		queryList.forEach(query->{
			
			ArrayList<String> maskList = query.getMaskList();
			Iterator<String> masksNameIterator =maskList.iterator();
			while(masksNameIterator.hasNext()) {
				String maskNameInQueryMaskList = masksNameIterator.next();
				if(maskNameInQueryMaskList.compareTo(maskName)==0) {
					maskList.remove(maskName);
				}
			}
			LOG.info("Updating query "+query.getQueryName());
			query.setMaskList(maskList);
		});
		
	}
	
	public long countMask() {
		LOG.info("Returning a count of mask");
		return maskRepository.count();
	}
}
