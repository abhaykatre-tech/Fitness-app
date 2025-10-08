package com.example.ai_service.service;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
// ******** is  service ka kam hai ki gemini api call kre or jo text hai wo ai_activity service se aa rha hai(same project service)
// or usi service ko response string form me return krna
@Service
@NoArgsConstructor
public class GeminiService {

    private  WebClient webClient;
    @Value("${gemini.url}")
    private  String gemini_url;
    @Value("${gemini.key}")
    private String gemini_key;

    //this is just for injecting web client  means here dependency injection happened
    @Autowired
    public GeminiService(WebClient.Builder builder){
        this.webClient=builder.build();
    }


    public  String getRecommendations(String text){
        //we can do this thing using classes also
        Map<String,Object> requestBody= Map.of("contents",new Object[]{
                Map.of("parts",new Object[]{
                        Map.of("text",text)
                })

        });
//public  String getRecommendations(String text){
//   Text text1=new Text();
//   text1.setText(text);
//   List<Text> list_text=new ArrayList<>();
//   list_text.add(text1);
//
//   Part part =new Part();
//   part.setParts(list_text);
//
//   Content content=new Content();
//    List<Part> list_part=new ArrayList<>();
//    list_part.add(part);
//
//   content.setContents(list_part);
//



        return  webClient.post()
                .uri(gemini_url)
                .header("Content-Type","application/json")
                .header("x-goog-api-key",gemini_key)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }


}
