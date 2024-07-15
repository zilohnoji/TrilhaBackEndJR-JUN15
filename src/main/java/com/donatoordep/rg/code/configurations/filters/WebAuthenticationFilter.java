package com.donatoordep.rg.code.configurations.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.donatoordep.rg.code.configurations.filters.jwt.exceptions.JWTTokenExpiredException;
import com.donatoordep.rg.code.configurations.security.JWTTokenUtil;
import com.donatoordep.rg.code.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Component
public class WebAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;
    private final UserRepository repository;
    private final JWTTokenUtil tokenUtil;

    @Autowired
    public WebAuthenticationFilter(UserRepository repository, JWTTokenUtil tokenUtil, ObjectMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.tokenUtil = tokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            try {
                DecodedJWT decodedJWT = JWT.decode(token);
                Date expirationDate = decodedJWT.getExpiresAt();

                if (expirationDate.before(Date.from(Instant.now()))) {
                    this.setResponse(response, HttpStatus.FORBIDDEN, new JWTTokenExpiredException(decodedJWT, request.getRequestURI()));
                    return;
                }

                String login = tokenUtil.validateToken(token);
                UserDetails userDetails = repository.findByEmailForLoadUserDetails(login);
                SecurityContextHolder.getContext().setAuthentication(authentication(userDetails, userDetails.getAuthorities()));

            } catch (Exception e) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        return request.getHeader("Authorization") != null ? request.getHeader("Authorization").split(" ")[1] : null;
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    private void setResponse(HttpServletResponse response, HttpStatus status, Object exception) throws IOException {
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(this.convertObjectToJson(exception));
    }

    private UsernamePasswordAuthenticationToken authentication(UserDetails user, Collection<? extends GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}