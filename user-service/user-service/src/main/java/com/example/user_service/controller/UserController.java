package com.example.user_service.controller;

import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> userRegister(@Valid @RequestBody RegisterRequest registerRequest){
        UserResponse userResponse=userService.register(registerRequest);
        return new ResponseEntity<UserResponse>(userResponse,HttpStatusCode.valueOf(201));
    }

    @GetMapping("/{gmailId}")
    public ResponseEntity<UserResponse> findUserByEmailId(@PathVariable String gmailId){
        UserResponse userResponse=userService.findUser(gmailId);
        return new ResponseEntity<UserResponse>(userResponse,HttpStatusCode.valueOf(200));
    }
    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId){
        return   ResponseEntity.ok(userService.findUsingId(userId));

    }
}
