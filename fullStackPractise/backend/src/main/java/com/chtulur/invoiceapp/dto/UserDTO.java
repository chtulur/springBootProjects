package com.chtulur.invoiceapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//DTO stands for Data Transfer Object. We need this because your User object might have a
//lot of fields that you don't want to send to the frontend (password). You can create a new one.
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String title;
    private String bio;
    private boolean enabled;
    private boolean isNotLocked;
    private boolean isUsingMfa;
    private LocalDateTime createdAt;
    private String imageUrl;
}
