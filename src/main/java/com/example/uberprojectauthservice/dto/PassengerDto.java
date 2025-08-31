package com.example.uberprojectauthservice.dto;

import com.example.uberprojectauthservice.models.Passenger;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Date;

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
    private Date createdAt;

    public static PassengerDto from(Passenger p) {
        return PassengerDto.builder()
                .id(p.getId().toString())
                .name(p.getName())
                .email(p.getEmail())
                .password(p.getPassword())
                .phoneNumber(p.getPhoneNumber())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
