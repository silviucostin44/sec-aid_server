package com.example.secaidserver.worker;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class responsible for performing JWT operations like creation and validation of the token.
 */
@Component
public class JwtTokenUtil implements Serializable {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final long serialVersionUID = -2550185165626007488L;
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Retrieve username from jwt token.
     *
     * @param token the token
     * @return the username
     */
    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Generate token for user.
     *
     * @param userDetails the user details wrapper
     * @return the token
     */
    public String generateToken(final UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Validate token.
     *
     * @param token       the token
     * @param userDetails the user details wrapper
     * @return the truth validation value
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Retrieve expiration date from jwt token.
     *
     * @param token the token
     * @return the expiration date
     */
    private Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieving any information from token we will need the secret key.
     *
     * @param token the token
     * @return the claims
     */
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Check if the token has expired.
     *
     * @param token the token
     * @return the truth value
     */
    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * While creating the token
     * 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     * 3. According to JWS Compact
     * Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     * Compaction of the JWT to a URL-safe string
     *
     * @param claims  the claims
     * @param subject the subject
     * @return the token
     */
    private String doGenerateToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }
}
