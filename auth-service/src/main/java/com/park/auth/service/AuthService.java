package com.park.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.park.auth.dto.ApiResponse;
import com.park.auth.dto.LoginRequest;
import com.park.auth.dto.UserDto;
import com.park.auth.repository.UserServiceClient;
import com.park.auth.util.JwtUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class AuthService {

    @Autowired
    private UserServiceClient userClient;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @CircuitBreaker(
            name = "userService",
            fallbackMethod = "userFallback")
    @Retry(
            name = "userService",
            fallbackMethod = "userFallback")
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
    
    public String userFallback(
    		LoginRequest request,
            Exception ex) {

       // UserResponse response = new UserResponse();
        //response.setId(id);
        //response.setName("Service Unavailable");
    	System.out.println("Error ->"+ex.getMessage());
    	logger.error(ex.getMessage());
        return ex.getMessage();
    }
    
    public String userCreationFallback(
    		UserDto userDto,
            Exception ex) {

       // UserResponse response = new UserResponse();
        //response.setId(id);
        //response.setName("Service Unavailable");
    	System.out.println("Error ->"+ex.getMessage());
    	logger.error(ex.getMessage());
        return ex.getMessage();
    }

    @CircuitBreaker(
            name = "userService",
            fallbackMethod = "userCreationFallback")
    @Retry(
            name = "userService",
            fallbackMethod = "userCreationFallback")
    public String createUser(UserDto userDto) {
    	System.out.println("User Dto -> "+userDto);
    	
    	ResponseEntity<ApiResponse<UserDto>> apiRespose = userClient.createUser(userDto);
    	return apiRespose.getBody().getMessage();

        //return jwtUtil.generateToken(apiRespose.getBody().getData().getUserName(), apiRespose.getBody().getData().getRolesIds()); 
    }	
}
