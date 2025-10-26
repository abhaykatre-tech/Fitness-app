package com.example.active_service.controller;

import com.example.active_service.dto.ActivityRequest;
import com.example.active_service.dto.ActivityResponse;
import com.example.active_service.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActiveController {
    private ActivityService activityService;
    @Autowired // (Optional if using constructor injection)
    public ActiveController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/track/activity")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest, @RequestHeader("X-User-ID") String userId){
        activityRequest.setUserId(userId);
        return  ResponseEntity.ok(activityService.trackActivity(activityRequest));
            }
    @GetMapping("get/activity")
    public ResponseEntity<List<ActivityResponse>> getActivity(@RequestHeader("X-User-ID") String userId){
        List<ActivityResponse> activities= activityService.findActivity(userId);
        return ResponseEntity.ok(activities);
    }
}
