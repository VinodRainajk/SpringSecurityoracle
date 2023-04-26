package com.wellsfargo.security.Filter;

import com.wellsfargo.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

     private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authheader = request.getHeader("Authorization");

        if(authheader==null || !authheader.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String jwtToken = authheader.substring(7);
        String email = jwtService.extractemail(jwtToken);
        
        



    }
}
