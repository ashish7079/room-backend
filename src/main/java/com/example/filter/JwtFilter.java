package com.example.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.util.JwtUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil utils;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil utils, UserDetailsService userDetailsService) {
        this.utils = utils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

    	
    	  String path = request.getServletPath();

    	    // 🔥 IMPORTANT FIX
    	    if (path.startsWith("/images/")) {
    	        filterChain.doFilter(request, response);
    	        return;
    	    }
    	
        String authHeader = request.getHeader("Authorization");
        String userName = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                userName = utils.ExtractUserName(token);
            } catch (Exception e) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            System.out.println("TOKEN: " + token);
            System.out.println("USERNAME: " + userName);
            System.out.println("Authorities: " + userDetails.getAuthorities());
            
            if (utils.validateToken(userDetails.getUsername(), token)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}