package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Query;
import com.example.demo.service.QueryService;

@CrossOrigin(origins = "*")
@RestController
public class QueryController {

	@Autowired
	private QueryService queryService;
	
	@RequestMapping("/queries/create")
	public Query create(@RequestBody Query query) {
		return queryService.create(query);
	}
	
	@RequestMapping("/queries/{queryName}")
	public Query getQuery(@PathVariable String queryName) {
		return queryService.getByQueryName(queryName);
	}
	@RequestMapping("/queries/getAll")
	public List<Query> getAll(){
		return queryService.getAll();
	}
	
	@PutMapping("/queries/{queryName}")
	public Query update(@PathVariable String queryName,@RequestBody Query queryDetails) {
		return queryService.update(queryName, queryDetails);
	}
	
	@DeleteMapping("/queries/{queryName}")
	public void delete(@PathVariable String queryName) {
		queryService.delete(queryName);
	}
	@RequestMapping ("/queries/deleteAll")
	public String deleteAll() {
		queryService.deleteAll();
		return "Deleted all records";
	}
	@RequestMapping("/queries/count")
	public long count() {
		return queryService.count();
	
	}
	
}
