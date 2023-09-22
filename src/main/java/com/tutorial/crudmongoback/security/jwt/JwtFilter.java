package com.tutorial.crudmongoback.security.jwt;

import com.tutorial.crudmongoback.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String token= getToken(req);
        try {
            if (token!=null && jwtProvider.validationToken(token)){
                String username=jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails= userDetailsServiceImpl.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (UsernameNotFoundException e) {
            logger.error("filter blocked request");
        }
        chain.doFilter(req,res);

    }

    private String getToken(HttpServletRequest req) {
        String header= req.getHeader("Authorization");
        if (header!=null && header.startsWith("Bearer "))
            return header.replace("Bearer ","");
        return null;
    }
}
