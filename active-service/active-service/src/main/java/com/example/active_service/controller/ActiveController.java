package com.example.active_service.controller;

import com.example.active_service.dto.ActivityRequest;
import com.example.active_service.dto.ActivityResponse;
import com.example.active_service.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActiveController {
    private ActivityService activityService;

    @PostMapping("/track/activity")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest){
        return  ResponseEntity.ok(activityService.trackActivity(activityRequest));
            }
}
