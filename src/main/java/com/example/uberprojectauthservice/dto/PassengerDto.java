package com.example.uberprojectauthservice.dto;

import com.example.uberprojectentityservice.models.Passenger;
import lombok.*;
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
