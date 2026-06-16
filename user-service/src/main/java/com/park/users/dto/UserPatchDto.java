package com.park.users.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPatchDto {
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String userName;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Email(message = "Invalid email format")
    private String email;

    private Set<Long> roles;
}
