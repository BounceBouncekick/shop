package com.example.jwtjwt.sevice;

import com.example.jwtjwt.dto.JoinDTO;
import com.example.jwtjwt.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public String authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("authentication"+ authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("userDetails" + userDetails);

            return jwtUtil.createJwt(username, userDetails.getAuthorities().toString(), 60*60*10L);
        } catch (AuthenticationException e) {
            return null; // 인증 실패 시 null 반환
        }
    }

}