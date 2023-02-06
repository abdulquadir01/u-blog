package com.aq.blogapp.config.security;

import com.aq.blogapp.utils.constants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenHelper {

    private final String seceret = "jwtTokenKey";

    //  retrieving username from token
    public String getUsernameFromToken(String token) {
        System.out.println("token in helper class: " + token);
        System.out.println("username in helper class: " + getClaimFromToken(token, Claims::getSubject));

        return getClaimFromToken(token, Claims::getSubject);
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //  get all data from the token - header, body & signature
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //    secret key is used to retrieve info - We're parsing the token to get the info within the token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(seceret).parseClaimsJws(token).getBody();
    }

    //    to check token has expired or not
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //    to generate token for user
    public String tokenGenerator(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * steps to create a token -
     * 1. Define claims of the token, like Issuer, Expiration, Subject and ID
     * 2. Sign the JWT using the HS512 algo and secret key.
     * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-16
     * compacting of the JWT to a URL-safe string
     *
     * @param claims
     * @param subject
     * @return jwt token
     */


    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AppConstants.JWT_TOKEN_VALIDITY * 100))
                .signWith(SignatureAlgorithm.HS512, seceret)
                .compact();
    }


    //    validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
