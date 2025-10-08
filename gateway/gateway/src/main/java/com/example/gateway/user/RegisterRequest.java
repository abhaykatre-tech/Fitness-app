package com.example.gateway.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "email required")
    @Email(message = "please give email format")
    private String email;
    private String keycloakId;
    @NotBlank(message = "password required")
    @Size(min = 6,max = 10,message = "password required")
    private String password;

    private  String firstName;
    private String lastName;
}
