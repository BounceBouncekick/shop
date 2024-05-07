package com.example.gatewayserver.filter;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;


@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;

    // 고정된 시크릿 키
    private static final String SECRET_KEY = "vmfhaltmskdlstkfkdgodyroqkfwkdbalroqkfwkdbalaaaaaaaaaaaaaaaabbbbb"; // 적절한 시크릿 키로 대체해야 합니다.

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {
        // 필요한 경우 설정 옵션을 추가할 수 있습니다.
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter = (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 클라이언트 요청의 HTTP 메소드 및 경로 로깅
            log.info("Received request: {} {}", request.getMethod(), request.getPath());

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                // 요청 헤더에 포함된 Authorization 헤더 및 JWT 값 로깅
                log.error("No Authorization header");
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer ", "");

            // 요청 헤더에 포함된 Authorization 헤더 및 JWT 값 로깅
            log.info("authorizationHeader : " + authorizationHeader);
            log.info("jwt : " + jwt );

            if (!isJwtValid(jwt)) {
                log.error("JWT Token is not valid");
                return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };

        return filter;
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = false;

        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET_KEY.getBytes());
            Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);
            Claims claims = jws.getBody();

            // JWT 토큰에서 Subject 가져오기
            String subject = jwtParser.parseClaimsJws(jwt).getBody().getSubject();
            log.info("Subject: " + subject);

            // 발급 시간(iat) 및 만료 시간(exp) 가져오기
            Date issuedAt = claims.getIssuedAt();
            Date expiration = claims.getExpiration();
            log.info("Issued At: " + issuedAt);
            log.info("Expiration: " + expiration);

            if (subject != null) {
                returnValue = true;

            } else {
                log.error("Subject is null in the JWT token");
            }
        } catch (Exception e) {
            returnValue = false;
            e.printStackTrace();
            log.error("JWT token parsing error: " + e.getMessage());
        }

        return returnValue;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }
}