package com.example.ai_service.service;

import com.example.ai_service.model.Activity;
import com.example.ai_service.model.Recommendation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

// work of this service is to process response of ai
// call gemini_service and take response from it
//work on it and in last return  to activity listener and save in mongodb

@Service
@Slf4j
@AllArgsConstructor
public class AIActivityService {

    private GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity){
        String prompt=generatePrompt(activity);
        String geminiResponse=  geminiService.getRecommendations(prompt);

       log.info("+**GEMINI RESPONSE** "+geminiResponse);
        return processAiResponse(activity,geminiResponse);
    }

    private Recommendation processAiResponse(Activity activity, String geminiResponse) {
        ObjectMapper mapper=new ObjectMapper();
        try {
            JsonNode rootNode=mapper.readTree(geminiResponse);
            JsonNode textNode=rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");
            String realApi=textNode.asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("\\n```", "")
                    .trim();
            // this is for recommendation
            StringBuilder recommendation=new StringBuilder();
            JsonNode analysisJson=mapper.readTree(realApi);
            JsonNode analysisNode=analysisJson.path("analysis");
            processAnalysisNode(analysisNode,recommendation,"overall","Overall: ");
            processAnalysisNode(analysisNode,recommendation,"pace","Pace: ");
            processAnalysisNode(analysisNode,recommendation,"heartRate","Heart Rate: ");
            processAnalysisNode(analysisNode,recommendation,"caloriesBurned","CaloriesBurned: ");

        //for IMPROVEMENT  *******
            JsonNode improvementNode=analysisJson.path("improvements");
            List<String> improvements= processImprovements(improvementNode,"area","recommendation");

       // for suggestions ********
            JsonNode suggestionsNode=analysisJson.path("suggestions");
            List<String> suggestions= processSuggestions(suggestionsNode,"workout","description");

        // for safety   **********
            JsonNode safetyNode=analysisJson.path("safety");
            List<String> safety=processSafety(safetyNode);

            //*********now add this data into recommendation class
            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getType())
                    .recommendations(recommendation.toString().trim())
                    .improvement(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }

        // return null just for error

    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return  Recommendation.builder().activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getType())
                .recommendations("Unable to generate detailed analysis")
                .improvement(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider consulting a fitness professional"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> processSafety(JsonNode safetyNode) {
        List<String> safety_list=new ArrayList<>();
        if(safetyNode.isArray()){
            safetyNode.forEach(safetyItem->{
                safety_list.add(safetyItem.asText());
            });
        }
        return safety_list.isEmpty()?
                Collections.singletonList("Follow general safety guidelines"):
                safety_list;

    }

    private List<String> processSuggestions(JsonNode suggestionsNode, String workout, String description) {
        List<String> list_suggestion= new ArrayList<>();
        if(suggestionsNode.isArray()){
            suggestionsNode.forEach(suggestion->{
                String workout1=suggestion.path(workout).asText();
                String description1=suggestion.path(description).asText();
                list_suggestion.add(String.format("%s : %s",workout1,description1));
            });
        }
        return list_suggestion.isEmpty()?
                Collections.singletonList("No specific suggestions provided") :
                list_suggestion;


    }

    private List<String> processImprovements(JsonNode improvementNode, String area, String recommendation) {
        List<String> list_improvement= new ArrayList<>();
      if(improvementNode.isArray()){
          improvementNode.forEach((improvement)->{
              String areas=improvement.path(area).asText();
              String details=improvement.path(recommendation).asText();
              list_improvement.add(String.format("%s : %s",areas,details));
          });
      }
            return list_improvement.isEmpty()
                    ? Collections.singletonList("No specific improvements provided")
                     :list_improvement;


    }

    private void processAnalysisNode(JsonNode analysisNode, StringBuilder sb, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()) {
            String str = analysisNode.path(key).asText();
            sb.append(prefix)
                    .append(str)
                    .append("\n\n");
        }
    }

    private String generatePrompt(Activity activity) {
       return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
   
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above. give hindi response
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()

        );
    }
}
