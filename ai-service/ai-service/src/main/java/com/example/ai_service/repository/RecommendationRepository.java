package com.example.ai_service.repository;

import com.example.ai_service.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation,String> {
    //jaise mysql me jpa use hota hai mongodb me Spring Data MongoDB 🚀 for generate bson
    List<Recommendation> findByUserId(String userId);

    Optional<Recommendation>findByActivityId(String activityId);
}
