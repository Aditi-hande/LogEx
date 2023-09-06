package com.example.demo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Target;

@Repository
public interface TargetRepository extends MongoRepository<Target, String>{
   public Target findByTargetName(String targetName);
   public long count();
}

