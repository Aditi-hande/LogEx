package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Mask;

@Repository
public interface MaskRepository extends MongoRepository<Mask, String>{
   public Mask findByMaskName(String maskName);
   public long count();
}

