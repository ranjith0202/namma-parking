package com.park.auth.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
//@ToString(exclude = "password")
public class UserDto extends BaseDto{
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String userName;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "At least one role is required")
    //private Set<Roles> roles;
    private Set<Long> rolesIds; 

   // private String password;
 //   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  //  private Set<Roles> roles;
    


}
