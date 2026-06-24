package com.park.gateway;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;


@Component
public class JwtAuthenticationFilter implements WebFilter {

	@Autowired
	JwtUtil jwtUtil;
	
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	
	 private static final String GATEWAY_MARKER = "X-GATEWAY";
	 private static final String USER_HEADER = "X-User-Name";
	 private static final String ROLE_HEADER = "X-User-Role";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        if (HttpMethod.OPTIONS.equals(exchange.getRequest().getMethod())) {
            return chain.filter(exchange);
        }
        
        String correlationId = exchange.getRequest().getHeaders().getFirst("X-Correlation-Id");
        if (correlationId == null) {
            correlationId = "NA";
        }

        log.info("Request path ->{} [{}]", path, correlationId);

        
        log.info("Request path  ->"+path);
        if (path.startsWith("/auth/") || path.startsWith("/api/users/internal/")) {
            return chain.filter(exchange);
        }
        

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return unauthorized(exchange);
        }

        String username = jwtUtil.extractUsername(token);
        List<String> roles = jwtUtil.extractRoles(token);

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        
        Authentication auth = new UsernamePasswordAuthenticationToken(
                username,
                token,
                authorities
        );
        
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(USER_HEADER, username)
                .header(ROLE_HEADER, String.join(",", roles))
                .header(GATEWAY_MARKER, "true")
                .build();


       // ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header(USER_HEADER, username).build();
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        return chain.filter(mutatedExchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }
    

    
    // 🔥 ADD THIS METHOD
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
