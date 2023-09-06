package com.example.demo.service;

import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.Query;
import com.example.demo.model.Schedule;
import com.example.demo.repository.QueryRepository;

@Service
public class QueryService {
   
	static Logger LOG = LogManager.getLogger(QueryService.class);
	
	@Autowired
	private QueryRepository queryRepository;
	
	@Autowired
	private ScheduleService scheduleService;
	
	
	//Create operation
	public Query create(Query query) {
		
		Schedule s = scheduleService.getByscheduleName(query.getScheduleName());
		if(s.getQueryList()==null) {
			HashSet<String> queryList = new HashSet<String>();
			queryList.add(query.getQueryName());
			s.setQueryList(queryList);
			scheduleService.update(s.getName(), s);
		}
		else {
			HashSet<String> queryList = s.getQueryList();
			queryList.add(query.getQueryName());
			s.setQueryList(queryList);
			scheduleService.update(s.getName(), s);
		}
		
		Query savedQuery = queryRepository.save(query);
		LOG.info("Saving query ");
		return savedQuery;
	}
	

	public List<Query> getAll(){
		List<Query> queryList = queryRepository.findAll();
		LOG.info("Retreiving all queries");
		return queryList;
	}
	
	public Query getByQueryName(String queryName) {
		Query singleQuery = queryRepository.findByQueryName(queryName);
		LOG.info("Searching for query by name");
		return singleQuery;
	}
	
	//Update operation
	public Query update(String queryName,Query queryDetails) {
		LOG.info("Updating a query");
		Query q = queryRepository.findByQueryName(queryName);
		q.setProduct(queryDetails.getProduct());
		q.setDescription(queryDetails.getDescription());
		q.setQueryText(queryDetails.getQueryText());
		q.setElasticSearchIndex(queryDetails.getElasticSearchIndex());
		q.setMaskList(queryDetails.getMaskList());
		q.setTargetName(queryDetails.getTargetName());
		q.setScheduleName(queryDetails.getScheduleName());
		if(q.getScheduleName()!=null) {
			Schedule s = scheduleService.getByscheduleName(q.getScheduleName());
			if(s.getQueryList()==null) {
				HashSet<String> queryList = new HashSet<String>();
				queryList.add(q.getQueryName());
				s.setQueryList(queryList);
				scheduleService.update(s.getName(), s);
			}
			else {
				HashSet<String> queryList = s.getQueryList();
				queryList.add(q.getQueryName());
				s.setQueryList(queryList);
				scheduleService.update(s.getName(), s);
			}
			
		}
		return queryRepository.save(q);
	}
	//Delete operation
	public void deleteAll() {
		LOG.info("Deleting all queries");
		queryRepository.deleteAll();
	}
	
	public void delete(String queryName) {
		LOG.info("Deleting a single query");
		Query p = queryRepository.findByQueryName(queryName);
		Schedule s = scheduleService.getByscheduleName(p.getScheduleName());
		if(s!=null) {
			LOG.info("Updating associated schedules");
			if(s.getQueryList()!=null){
				HashSet<String> queryList = s.getQueryList();
				queryList.remove(queryName);
				s.setQueryList(queryList);
				scheduleService.update(s.getName(), s);
			}
		}
		queryRepository.delete(p);
	}
	
	public long count() {
		LOG.info("Retriving a count of queries");
		return this.queryRepository.count();
	
	}
}
