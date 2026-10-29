package com.Atos.Webaccess.security.filters;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Atos.Webaccess.Constants.AppConstants;
import com.Atos.Webaccess.security.entities.AuthToken;
import com.Atos.Webaccess.security.services.AuthTokenService;
import com.Atos.Webaccess.User.Entities.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;

    public TokenAuthenticationFilter(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String token = request.getHeader(AppConstants.AUTH_TOKEN_NAME);

        // No token: continue without token authentication.
        // Spring Security will enforce endpoint authorization.
        if (token == null || token.isBlank()) {
            chain.doFilter(request, response);
            return;
        }

        AuthToken authToken = authTokenService.findById(token);

        if (authToken == null) {
            reject(response, "Invalid authentication token");
            return;
        }

        if (authTokenService.isTokenExpired(authToken)) {
            authTokenService.delete(authToken);
            reject(response, "Authentication token expired");
            return;
        }

        User user = authToken.getUser();

        if (user == null) {
            reject(response, "Invalid authentication token");
            return;
        }

        Collection<? extends GrantedAuthority> authorities =
                user.getAuthorities();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        authorities
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        authTokenService.update(authToken);

        chain.doFilter(request, response);
    }

    private void reject(
            HttpServletResponse response,
            String message) throws IOException {

        SecurityContextHolder.clearContext();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                "{\"message\":\"" + message + "\"}"
        );
    }
}