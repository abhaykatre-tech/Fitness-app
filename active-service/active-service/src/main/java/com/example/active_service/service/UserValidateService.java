package com.example.active_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidateService {
    public final WebClient webClient;

    public boolean userValidate(String userId){
        try {
            return Boolean.TRUE.equals(webClient.get()
                    .uri("/api/users/validate/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        }
        catch (WebClientResponseException e){
            throw  new RuntimeException("web client exception");
        }

    }


}
