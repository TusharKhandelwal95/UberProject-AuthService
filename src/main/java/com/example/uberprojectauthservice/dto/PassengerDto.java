package com.example.uberprojectauthservice.dto;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PassengerDto {

    private String id;
    private String name;
    private String email;
    private String password; // encrypted password
    private String phoneNumber;
    private String createdAt;
}
