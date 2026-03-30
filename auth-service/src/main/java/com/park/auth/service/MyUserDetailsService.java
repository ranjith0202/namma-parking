package com.park.auth.service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.park.auth.repository.UserServiceClient;
import com.park.common.dto.UserDto;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;

    public MyUserDetailsService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userServiceClient.getUser(username);

        if (userDto == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        final Map<Long, String> ROLE_MAP = Map.of(
        	    1L, "ADMIN",
        	    2L, "USER",
        	    3L, "MANAGER"
        	);

        
        Set<SimpleGrantedAuthority> authorities = userDto.getRolesIds().stream()
                .map(roleId -> new SimpleGrantedAuthority("ROLE_" + ROLE_MAP.get(roleId)))
                .collect(Collectors.toSet());


        return User.builder()
                .username(userDto.getUserName())
                .password(userDto.getPassword())   // must already be encoded!
                .authorities(authorities)       // maps to authorities
                .build();
    }
}