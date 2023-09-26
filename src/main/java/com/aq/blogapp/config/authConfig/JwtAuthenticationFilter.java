package com.aq.blogapp.config.authConfig;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper tokenHelper;

//    public JwtAuthenticationFilter(
//            UserDetailsService userDetailsService,
//            JwtTokenHelper tokenHelper
//    ){
//        this.userDetailsService = userDetailsService;
//        this.tokenHelper = tokenHelper;
//    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

//        1. get token
        String requestToken = request.getHeader("Authorization");

//      token starts with "Bearer <some-string>"
        System.out.println("requestToken: \n " + requestToken);

        String username = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer")) {

            token = requestToken.substring(7);
            System.out.println("originalToken: \n " + token);

            try {
                username = tokenHelper.getUsernameFromToken(token);
                System.out.println("username :" + username);
            } catch (IllegalArgumentException ex) {
                System.out.println("unable to get JWT token");
            } catch (ExpiredJwtException ex) {
                System.out.println("JWT token has expired");
            } catch (MalformedJwtException ex) {
                System.out.println("invalid jwt");
            }

        } else {
            System.out.println("token doesn't starts with Bearer");
        }

//        validating token after retrieval
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (tokenHelper.validateToken(token, userDetails)) {

//                set authentication
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

            } else {
                System.out.println("Invalid token");
            }
        } else {
            System.out.println("Username is null or context is not null");
        }

        filterChain.doFilter(request, response);

    }
}
