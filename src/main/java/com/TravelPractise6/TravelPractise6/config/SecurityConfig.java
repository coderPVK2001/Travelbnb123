package com.TravelPractise6.TravelPractise6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JwtFilterRequest jfr;

    public SecurityConfig(JwtFilterRequest jfr) {
        this.jfr = jfr;
    }

    @Bean
    SecurityFilterChain sfc(HttpSecurity http) throws Exception{

        http.csrf().disable().cors().disable();

        http.authorizeHttpRequests()
                .requestMatchers("/api/travel6/add","/api/travel6/verify","/api/travel6/csv").permitAll()
                .requestMatchers("/api/travel6/prop/addproperty","/api/travel6/prop/delete","/api/travel6/prop/update","/travel6/aws/list","/travelapi6/awsbucket/upload","/travel6/v1/image/upload/property").hasRole("ADMIN")
                .requestMatchers("/api/travel6/prop/searchproperty"
                                         ,"/api/travel6/review/add","/api/travel6/review/get","/api/travel6/review/reviewslist","/api/travel6/review/deletereview"
                                         ,"/travelapi6/favourites/add","/travelapi6/favourites/getallfavourites","/travelapi6/favourites/dislike"
                                         ,"/api/travel6/v1/bookings/add","/api/travel6/v1/bookings/genpdf").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated();

        http.addFilterBefore(jfr, AuthorizationFilter.class);
        return http.build();
    }
}
