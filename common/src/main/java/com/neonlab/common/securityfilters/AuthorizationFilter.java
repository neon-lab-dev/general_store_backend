package com.neonlab.common.securityfilters;

import com.neonlab.common.constants.GlobalConstants;
import com.neonlab.common.services.AuthUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AuthUserService authUserService;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final int TOKEN_START_INDEX = 7;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(AUTHORIZATION);
        String token = null;
        if (Objects.nonNull(authHeader) && authHeader.startsWith(BEARER)){
            token = authHeader.substring(TOKEN_START_INDEX);
        }
        if (Objects.nonNull(token) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
            var mayBeUser = Optional.ofNullable(authUserService.getActiveByToken(token));
            mayBeUser.ifPresent(authUser -> setSecurityContextHolder(request, authUser));
        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContextHolder(HttpServletRequest request, UserDetails userDetails){
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
