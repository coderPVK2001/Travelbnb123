package com.TravelPractise6.TravelPractise6.config;

import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import com.TravelPractise6.TravelPractise6.repository.UserdetailsRepository;
import com.TravelPractise6.TravelPractise6.service.JwtService;
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

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    private JwtService jservice;
    private UserdetailsRepository udetails;

    public JwtFilterRequest(JwtService jservice, UserdetailsRepository udetails) {
        this.jservice = jservice;
        this.udetails = udetails;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token!=null && token.startsWith("Bearer")){

            String ss = token.substring(7);
            String getusername = jservice.getusername(ss);
            Optional<Userdetails> opt = udetails.findByUsername(getusername);
            Userdetails userdetails = opt.get();

            UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                    userdetails,
                    null,
                    Collections.singleton(new SimpleGrantedAuthority(userdetails.getRole()))
            );

            authToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
        filterChain.doFilter(request,response);
    }

//    SecurityContextHolder.getContext():
//Accesses the current security context, which is a thread-local storage for the security information.


}
