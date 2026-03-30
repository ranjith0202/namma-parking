package com.park.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.park.auth.dto.LoginRequest;
import com.park.auth.repository.UserServiceClient;
import com.park.auth.util.JwtUtil;
import com.park.common.dto.ApiResponse;
import com.park.common.dto.UserDto;

@Service
public class AuthService {

    @Autowired
    private UserServiceClient userClient;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }



    public String login(LoginRequest request) {
    	System.out.println("request -> "+request.getUserName());
    	
        UserDto apiResponse = userClient.getUser(request.getUserName());
System.out.println("request password ->"+request.getPassword());
System.out.println("apiResponse.getPassword  ->"+apiResponse.getPassword());
        if (!BCrypt.checkpw(request.getPassword(), apiResponse.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        
        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            apiResponse.getPassword()
                    )
                );

        return jwtUtil.generateToken(
                authentication.getName(),
                authentication.getAuthorities()
        );

    }
    
    public String createUser(UserDto userDto) {
    	System.out.println("User Dto -> "+userDto);
    	
    	ResponseEntity<ApiResponse<UserDto>> apiRespose = userClient.createUser(userDto);
    	return apiRespose.getBody().getMessage();

        //return jwtUtil.generateToken(apiRespose.getBody().getData().getUserName(), apiRespose.getBody().getData().getRolesIds()); 
    }	
}
