package com.TravelPractise6.TravelPractise6.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry}")
    private int expiry;

    private Algorithm algorithm;

    private String USERNAME="username";

    @PostConstruct
    public void construct(){

        algorithm = Algorithm.HMAC256(key);
    }

    public String generateToken(String username){

        return JWT.create()
                .withClaim(USERNAME,username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getusername(String token){

        DecodedJWT dd = JWT.require(algorithm).withIssuer(issuer).build().verify(token);

        String username = dd.getClaim(USERNAME).asString();
        return username;
    }

}
