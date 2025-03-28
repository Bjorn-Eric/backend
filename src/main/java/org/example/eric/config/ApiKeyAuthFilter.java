package org.example.eric.config;

import org.example.eric.model.User;
import org.example.eric.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;

    public ApiKeyAuthFilter(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader("X-Api-Key");

        if (apiKey == null || apiKey.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key missing");
            return;
        }

            Optional<User> optionalUser = userDetailsService.getUserByApiKey(apiKey);
        if(optionalUser.isEmpty()){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key invalid");
            return;
        }

                User user = optionalUser.get();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
