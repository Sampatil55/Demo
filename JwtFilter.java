package com.hotel.config;

import com.hotel.entity.User;
import com.hotel.respository.UserRepository;
import com.hotel.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component      //
public class JwtFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JWTService jwtService,
                     UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

   /* @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {//url will come here
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(8, token.length() - 1);


            String username = jwtService.getUsername(jwtToken);

            Optional<User> opUsername = userRepository.findByUsername(username);
            if (opUsername.isPresent()) {
                User user = opUsername.get();
                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));   //comes from springsecurity //it is roles implementation
                userToken.setDetails(new WebAuthenticationDetails(request));

                 SecurityContextHolder.getContext().setAuthentication(userToken);// it w
            }

        }
        filterChain.doFilter(request, response);
    } */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {//url will come here
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(8, token.length() - 1);


            String useremail = jwtService.getUsername(jwtToken);

            Optional<User> opUseremail = userRepository.findByUsername(useremail);
            if (opUseremail.isPresent()) {
                User user = opUseremail.get();
                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));   //comes from springsecurity //it is roles implementation
                userToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(userToken);// it w
            }

        }
        filterChain.doFilter(request, response);
    }


}

