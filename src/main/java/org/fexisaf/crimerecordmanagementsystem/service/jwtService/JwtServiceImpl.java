package org.fexisaf.crimerecordmanagementsystem.service.jwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${secret.key}")
    private String SECRET_KEY;

    private static final long jwtExpiration = 3_600_000L;

    private static final long refreshJwtToken = 86_400_000L;

    private static final long signUpJwt = 600000L;

    @Override
    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    @Override
    public String generateSingUpToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails, signUpJwt);
    }

    @Override
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails, refreshJwtToken);
    }

    @Override
    public boolean isTokenExpired(UserDetails userDetails, String token){
        String user = extractUserName(token);
        return userDetails.getUsername().equals(user) && !isTokenValid(token);
    }

    private boolean isTokenValid(String token) {
        return isToken(token).before(new Date());
    }

    private Date isToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String generateToken(
            Map<String, Object> extraClaim,
            UserDetails userDetails,
            long time
    ){
        return Jwts.builder()
                .setClaims(extraClaim)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        time))
                .setSubject(userDetails.getUsername())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }
}
