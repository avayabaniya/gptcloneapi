package com.avayabaniya.gptclone.authentication.jwt;

import com.avayabaniya.gptclone.config.AppPropertiesConfig;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private final AppPropertiesConfig config;

    private static final Logger logger
            = LoggerFactory.getLogger(JwtUtils.class);

    private String jwtSecret;

    private int jwtExpirationMs;

    @Autowired
    public JwtUtils(AppPropertiesConfig config) {
        this.config = config;
    }

    public String generateJwtToken(UserDetails userDetails) {
        return this.generateTokenFromUserDetails(userDetails);
    }

    public String generateTokenFromUserDetails(UserDetails userDetails) {

        //this.jwtExpirationMs = this.config.getJwtExpirationMs();
        //if (this.jwtExpirationMs == 0) {
            this.jwtExpirationMs = 360000000; //1hr
       // }

        this.jwtSecret = this.config.getJwtSecret();
        if (this.jwtSecret == null) {
            this.jwtSecret = "123";
        }

        String encodedString = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
        );

        //Instant expDate = Instant.now().plusMillis(this.jwtExpirationMs);
        Date expDate = new Date(System.currentTimeMillis() + this.jwtExpirationMs);

        return Jwts.builder().setSubject(userDetails.getUsername())
                .setClaims(claims)//added claims
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, encodedString)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        this.jwtSecret = this.config.getJwtSecret();
        if (this.jwtSecret == null) {
            this.jwtSecret = "123";
        }
        String encodedString = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());
        return Jwts.parser().setSigningKey(encodedString).parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getAllClaimsFromToken(String token) {
        this.jwtSecret = this.config.getJwtSecret();
        if (this.jwtSecret == null) {
            this.jwtSecret = "123";
        }
        String encodedString = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());

        return Jwts.parser()
                .setSigningKey(encodedString)
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateJwtToken(String authToken) {
        try {

            /*this.jwtSecret = this.config.getJwtSecret();
            if (this.jwtSecret == null) {
                this.jwtSecret = "123";
            }
            String encodedString = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());*/
            return (!isTokenExpired(authToken)); //at this point jwt is already decoded using secret key

        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
