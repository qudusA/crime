package org.fexisaf.crimerecordmanagementsystem.service.jwtService;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String jwtToken);


    boolean isTokenExpired(UserDetails userDetails, String token);

    String generateToken(UserDetails userDetails);

    String generateSingUpToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);
}
