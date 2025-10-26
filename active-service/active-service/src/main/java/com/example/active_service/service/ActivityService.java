package com.example.active_service.service;

import com.example.active_service.dto.ActivityRequest;
import com.example.active_service.dto.ActivityResponse;
import com.example.active_service.model.Activity;
import com.example.active_service.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidateService userValidateService;
    private  final KafkaTemplate<String,Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topic;

    //TODO: business logic means talk with db
    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        if(!userValidateService.userValidate(activityRequest.getUserId())){
            throw new RuntimeException("invalid user id"+activityRequest.getUserId());
        }
        Activity activity=Activity.builder().
                userId(activityRequest.getUserId())
                .type(activityRequest.getType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .build();

        Activity savedActivity=activityRepository.save(activity);

        kafkaTemplate.send(topic,savedActivity.getId(),savedActivity);

        System.out.println("***************kafka send***********************");



        return mapToResponse(savedActivity);

    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response=new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setStartTime(activity.getStartTime());
        response.setDuration(activity.getDuration());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }


    public List<ActivityResponse> findActivity(String userId) {
        return activityRepository.findByUserId(userId);
    }
}
