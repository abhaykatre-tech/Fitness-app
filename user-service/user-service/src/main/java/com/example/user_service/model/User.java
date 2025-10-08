package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String keycloakId;
     @Column(unique = true,name = "email")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @Enumerated
    private UserRole role=UserRole.ADMIN;

   @CreatedDate
   private LocalDateTime createdAt;

   @LastModifiedDate
   private LocalDateTime updatedAt;

}
