package com.donatoordep.rg.code.configurations.filters;

import com.donatoordep.rg.code.configurations.security.JWTTokenUtil;
import com.donatoordep.rg.code.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Configuration
public class WebAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository repository;
    private final JWTTokenUtil tokenUtil;

    @Autowired
    public WebAuthenticationFilter(UserRepository repository, JWTTokenUtil tokenUtil) {
        this.repository = repository;
        this.tokenUtil = tokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            String login = tokenUtil.validateToken(token);
            UserDetails userDetails = repository.findByEmailForLoadUserDetails(login);

            SecurityContextHolder.getContext().setAuthentication(authentication(userDetails, userDetails.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        return request.getHeader("Authorization") != null ? request.getHeader("Authorization").split(" ")[1] : null;
    }

    private UsernamePasswordAuthenticationToken authentication(UserDetails user, Collection<? extends GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}