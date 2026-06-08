package com.park.auth.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.park.auth.config.FeignConfig;
import com.park.auth.dto.ApiResponse;
import com.park.auth.dto.UserDto;

@FeignClient(name = "USER-SERVICE",configuration = FeignConfig.class)
public interface UserServiceClient {
	@GetMapping("/api/users/internal/userName/{userName}")
	UserDto getUser(@PathVariable("userName") String userName);

    @PostMapping("/api/users")
    //ApiResponse<UserDto> createUser(@RequestBody UserDto userDto);
    ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto);

}
