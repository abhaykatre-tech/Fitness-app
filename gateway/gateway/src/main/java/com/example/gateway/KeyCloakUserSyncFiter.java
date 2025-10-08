package com.example.gateway;

import com.example.gateway.user.RegisterRequest;
import com.example.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class KeyCloakUserSyncFiter implements WebFilter {
    private  final UserService userService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        RegisterRequest registerRequest =getUserDetails(token);
        if (userId == null) {
            userId = registerRequest.getKeycloakId();
            System.out.println("************userid "+userId);
        }

        if (userId != null && token != null) {
            System.out.println("******enterd");
            String finalUserId = userId;
            RegisterRequest finalRegisterRequest = registerRequest;

            return userService.userValidate(userId)
                    .flatMap(exist -> {
                        if (!exist) {
                            if (finalRegisterRequest != null) {
                                return userService.registerUser(finalRegisterRequest)
                                        .then(Mono.empty());
                            } else {
                                return Mono.empty();
                            }
                        } else {
                            System.out.println("******user already exist****");
                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(() -> {
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-ID", finalUserId)
                                .build();
                        System.out.println("******** "+mutatedRequest.getHeaders().getFirst("X-User-ID"));
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    }));
        }

        // ❌ पहले null return था
        // ✅ अब हमेशा mono return होगा
        return chain.filter(exchange);
    }


    private RegisterRequest getUserDetails(String token) {
        String tokenWithoutBearer=token.replace("Bearer","").trim();
        try {
            SignedJWT signedJWT=SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claimsSet= signedJWT.getJWTClaimsSet();
            RegisterRequest request=new RegisterRequest();
            request.setEmail(claimsSet.getStringClaim("email"));
            //because header me password nhi dikhta
            request.setPassword("dummyyy");
            request.setFirstName(claimsSet.getStringClaim("given_name"));
            request.setLastName(claimsSet.getStringClaim("family_name"));
            request.setKeycloakId(claimsSet.getStringClaim("sub"));
            System.out.println("********"+claimsSet.getStringClaim("given_name"));
            return  request;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
