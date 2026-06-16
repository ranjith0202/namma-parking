package com.park.gateway;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class RequestContextGatewayFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestContextGatewayFilter.class);

    private static final String GATEWAY_MARKER = "X-GATEWAY";
    //private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_NAME_HEADER = "X-User-Name";
    private static final String ROLE_HEADER = "X-User-Role";
    private static final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        // 1️⃣ Read or generate correlation ID
        String correlationId = request.getHeaders().getFirst(CORRELATION_ID);
        
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        // 2️⃣ Read user info from headers (set by JwtAuthenticationFilter)
       // String userId = request.getHeaders().getFirst(USER_ID_HEADER);
       // if (userId == null) userId = "0";

        String userName = request.getHeaders().getFirst(USER_NAME_HEADER);
        if (userName == null) userName = "SYSTEM";

        String roles = request.getHeaders().getFirst(ROLE_HEADER);
        if (roles == null) roles = "ANONYMOUS";

        // 3️⃣ Mutate request to propagate headers downstream
        ServerHttpRequest mutatedRequest = request.mutate()
                .header(CORRELATION_ID, correlationId)
                //.header(USER_ID_HEADER, userId)
                .header(USER_NAME_HEADER, userName)
                .header(ROLE_HEADER, roles)
                .header(GATEWAY_MARKER, "true")
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        // 4️⃣ Put correlation ID into MDC for logging
        MDC.put("correlationId", correlationId);

        // 5️⃣ Log the incoming request
        log.info("Incoming request {} {}", request.getMethod(), request.getURI());
        final String finalCorrelationId = correlationId;

        // 6️⃣ Continue filter chain with Reactor context + clear MDC
        return chain.filter(mutatedExchange)
                .contextWrite(ctx -> ctx.put(CORRELATION_ID, finalCorrelationId))
                .doFinally(signal -> MDC.clear());
    }

    @Override
    public int getOrder() {
        return -1;  // run before most other filters except authentication
    }
}
