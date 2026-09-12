package com.hotel.apigateway.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Public APIs
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader =
                request.getHeader("Authorization");

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter()
                    .write("Missing or Invalid Token");

            return;
        }

        String token =
                authHeader.substring(7);

        try {

            if (!jwtUtil.validateToken(token)) {

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED);

                response.getWriter()
                        .write("Invalid Token");

                return;
            }

            Long userId =
                    jwtUtil.extractUserId(token);

            String email =
                    jwtUtil.extractEmail(token);

            String role =
                    jwtUtil.extractRole(token);

            request.setAttribute(
                    "X-User-Id",
                    String.valueOf(userId));

            request.setAttribute(
                    "X-User-Email",
                    email);

            request.setAttribute(
                    "X-User-Role",
                    role);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(
                                    new SimpleGrantedAuthority(
                                            "ROLE_" + role
                                    )
                            )
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception ex) {

            ex.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter()
                    .write("Invalid Token");
        }
    }
}