package com.example.ai_service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "recommendations")
@Builder
public class Recommendation {
    @Id
    private String id;
    private String activityId;
    private String userId;
    private ActivityType activityType;
    private String recommendations;
    private List<String> improvement;
    private List<String> suggestions;
    private List<String> safety;
    @CreatedDate
    private LocalDateTime createdAt;

}
