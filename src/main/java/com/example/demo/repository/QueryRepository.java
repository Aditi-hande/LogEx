package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Query;

@Repository
public interface QueryRepository extends MongoRepository<Query, String>{
   public Query findByQueryName(String queryName);
   public long count();
    List<Query> findByProduct(String product);
}
