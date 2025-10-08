package com.example.gateway.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    public final WebClient webClient;

    public Mono<Boolean> userValidate(String userId){
        try {
            return webClient.get()
                    .uri("/api/users/validate/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(WebClientResponseException.class,e->{
                        return  Mono.error(new RuntimeException("problem in user"));
                    });

        }
        catch (WebClientResponseException e){
            throw  new RuntimeException("web client exception");
        }

    }


    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
        System.out.println("calling ---------------user reg");

        return webClient.post()
                .uri("/api/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .doOnError(e -> {
                    // log original error
                    System.err.println("Error while calling user-service: " + e.getMessage());
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    // preserve original status & body
                    return Mono.error(new RuntimeException("User service error: " + e.getRawStatusCode()
                            + " " + e.getResponseBodyAsString(), e));
                })
                .onErrorResume(Exception.class, e -> {
                    // catch-all for other errors (connection refused etc.)
                    return Mono.error(new RuntimeException("User service call failed", e));
                });
    }

}
