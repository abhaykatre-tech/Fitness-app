package com.example.ai_service.controller;

import com.example.ai_service.model.Recommendation;
import com.example.ai_service.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/recommendation/")
public class RecommendationController {
    private  final RecommendationService recommendationService;
//for user recommendation all activity
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Recommendation>>  getAllRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getAllRecommendationUsingUserId(userId));
    }


// for user only according to activity

    @GetMapping("/users/activity/{activityId}")
    public ResponseEntity<Recommendation>  getActivityAccordingRecommendation(@PathVariable String activityId){
      return   ResponseEntity.ok(recommendationService.getActivityUsingActivityId(activityId));
    }

}
