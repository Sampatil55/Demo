package com.hotel.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

//jwt token generation and verify
//jwt dependency download in pom.xml

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")//@value comes from spring framework
    private String algorithmkey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value(("${jwt.expiry.duration}"))
    private int expiry;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmkey);

    }

    public String generateToken(String username){
        return JWT.create()
                .withClaim("name",username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm) //verify signature
                .withIssuer(issuer) //verify issuer
                .build()
                .verify(token);
        return decodedJWT.getClaim("name").asString();

    }
    public String generateTokenn(String useremail){
        return JWT.create()
                .withClaim("name",useremail)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUseremail(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm) //verify signature
                .withIssuer(issuer) //verify issuer
                .build()
                .verify(token);
        return decodedJWT.getClaim("name").asString();

    }


}
