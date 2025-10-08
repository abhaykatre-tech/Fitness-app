package com.example.ai_service.service;

import com.example.ai_service.model.Activity;
import com.example.ai_service.model.Recommendation;
import com.example.ai_service.repository.RecommendationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// api key- AIzaSyBsmbZt_eOmuFc8S0u6N5BEvK84MtSTdro
//AIzaSyBsmbZt_eOmuFc8S0u6N5BEvK84MtSTdro

@Slf4j
@Service
@AllArgsConstructor
public class ActivityListener {

    private  AIActivityService activityService;
    private RecommendationRepository recommendationRepository;
    @KafkaListener(topics = "${kafka.topic.name}",groupId = "activity-processor-group")
    public void getActivity(Activity activity){
        log.info("********************activity received *********"+activity.toString());
        Recommendation recommendation= activityService.generateRecommendation(activity);
        //saving in db
        recommendationRepository.save(recommendation);
    }
}
