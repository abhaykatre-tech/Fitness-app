package com.example.active_service.repository;

import com.example.active_service.dto.ActivityResponse;
import com.example.active_service.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity,String> {
    List<ActivityResponse> findByUserId(String userId);
}
