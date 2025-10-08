package com.example.ai_service.service;

import com.example.ai_service.model.Recommendation;
import com.example.ai_service.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private  final RecommendationRepository recommendationRepository;

    //this service fetch from database list<Recommendation> from mongodb
    public List<Recommendation> getAllRecommendationUsingUserId(String userId) {
       return  recommendationRepository.findByUserId(userId);

    }
    //this service only fetch one Recommendation from db according to activity
    public Recommendation getActivityUsingActivityId(String activityId) {
       return recommendationRepository.findByActivityId(activityId).orElseThrow(()->{
         return   new RuntimeException("no recommendation found");
        });

    }
}
