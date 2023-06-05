package com.chtulur.invoiceapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

import static jakarta.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long userId;
    
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email address. Please enter a valid one.")
    private String email;
    
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    
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

