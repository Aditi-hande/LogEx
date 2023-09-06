package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LogLine;

@Repository
public interface LogRepository extends ElasticsearchRepository<LogLine, String> {

	Page<LogLine> findAll(Pageable pageable);
	List<LogLine> findBy_id(String id);
}

