package org.example.microstoreprogetto.util.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.microstoreprogetto.util.configuration.webautenticationdetails.CustomWebAuthenticationDetailsSource;
import org.example.microstoreprogetto.util.generateJWTtoken.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

public class JWTAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JWTAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            String username = jwtUtil.ExtractUsername(token);
            String role = jwtUtil.getRoleFromToken(token);
            String userId = jwtUtil.getIdFromToken(token);

            if (username.isEmpty() || role.isEmpty() || userId.isEmpty()) {
                throw new RuntimeException("qualcuno dei dati estratti dal token risulta privo di valore o null.");
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        // all oggetto UsernamePasswordAuthenticationToken,
                        // oltre ai valori di default (username , credentials , authorities )
                        // posso passare valori custom che posso comunque riprendere nel controller tramite l'oggetto
                        //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        new UsernamePasswordAuthenticationToken(username, null, List.of(() -> "ROLE_" + role));
                authenticationToken.setDetails(new CustomWebAuthenticationDetailsSource().buildDetails(request, userId));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }
}
