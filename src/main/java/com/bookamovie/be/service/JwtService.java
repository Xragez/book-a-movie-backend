package com.bookamovie.be.service;

import com.bookamovie.be.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String SECRET_KEY = "fsdfadsdf";
    private final UserRepository userRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        val claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        val authorities = userDetails.getAuthorities();
        val roles = new ArrayList<>();
        for(val authority: authorities){
            roles.add(authority.getAuthority());
        }
        val user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("userId", user.getId());
        claims.put("firstName", user.getFirstName());
        claims.put("surname", user.getSurname());
        return createToken(claims, userDetails.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        val username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims)
                             .setSubject(subject)
                             .setIssuedAt(new Date(System.currentTimeMillis()))
                             .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private Boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
