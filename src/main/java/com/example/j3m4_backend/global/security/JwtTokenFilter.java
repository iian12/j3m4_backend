package com.example.j3m4_backend.global.security;

import com.example.j3m4_backend.global.auth.CustomUserDetailsService;
import com.example.j3m4_backend.global.utils.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        List<String> publicPaths = List.of(
                "/api/auth/login",
                "/api/users/join"
        );
        boolean isPublicPath = publicPaths.stream().anyMatch(path::startsWith);

        if (isPublicPath) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = TokenUtils.extractTokenFromRequest(request, "access_token");
        log.atInfo().log("Extracted Token: {}", token);
        if (token == null) {
            handleException(response, "MISSING_TOKEN", "Token is missing");
            return;
        }

        try {
            if (!jwtTokenProvider.validateToken(token)) {
                handleException(response, "INVALID_TOKEN", "Token is invalid");
                return;
            }

            Long userId = TokenUtils.getUserIdFromToken(token);

            log.info("Authenticated user ID: {}", userId);

            UserDetails userDetails = customUserDetailsService.loadUserById(userId);
            log.info("UserDetails: {}", userDetails);
            if (userDetails == null) {
                handleException(response, "USER_NOT_FOUND", "User not found");
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Authentication: {}", authentication);
        } catch (ExpiredJwtException e) {
            handleException(response, "TOKEN_EXPIRED", "Token has expired");
            return;
        } catch (SignatureException e) {
            handleException(response, "INVALID_SIGNATURE", "Token signature is invalid");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            handleException(response, "TOKEN_ERROR",
                    "An error occurred while processing the token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, String errorCode, String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                String.format("{\"error_code\": \"%s\", \"message\": \"%s\"}", errorCode, message));
    }
}
