package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Schedule;

@Repository
public interface ScheduleRepository extends  MongoRepository<Schedule, String>{
	public Schedule findByName(String name);
	public long count();
	List<Schedule> findByGroup(String group);
}
