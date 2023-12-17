package com.renstation.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Placeholder for Future JWT Authentication.
 * 
 * Future Implementation:
 * 1. Check if the route is public (e.g. /api/v1/auth/**)
 * 2. If protected, extract Bearer token from Authorization header.
 * 3. Validate token signature and expiration.
 * 4. Extract claims (userId, role) and attach as headers to downstream microservices.
 * 5. If invalid, throw UnauthorizedException (caught by GlobalExceptionHandler).
 */
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // TODO: Implement JWT validation logic
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0; // Runs after Correlation ID, before Routing
    }
}
