package com.park.parking.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.park.parking.util.UserContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            UserContext.setUser(request.getHeader("X-User-Name"));
            
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Map<String, String> contextMap = new HashMap<>();
            contextMap.put("correlationId", httpRequest.getHeader("X-Correlation-Id"));
            //contextMap.put("userId", httpRequest.getHeader("X-User-Id"));
            contextMap.put("userName", httpRequest.getHeader("X-User-Name"));
            contextMap.put("roles", httpRequest.getHeader("X-Roles"));
            
            UserContext.setContext(contextMap);
            MDC.put("correlationId", contextMap.get("correlationId"));
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
            MDC.clear();
        }
    }
}
