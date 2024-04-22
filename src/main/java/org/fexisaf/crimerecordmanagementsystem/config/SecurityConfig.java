package org.fexisaf.crimerecordmanagementsystem.config;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.TokenEntity;
import org.fexisaf.crimerecordmanagementsystem.jwtAuthFilter.JwtFilter;
import org.fexisaf.crimerecordmanagementsystem.repository.TokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Objects;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST =
            {"/login",
                    "/signup",
                    "/verification",
                    "/forgetpassword",
                    "/forgetpassword/inputOtp",
                    "/"
            };
    private final JwtFilter jwtFilter;
    private final TokenRepository tokenRepository;
    private final UserDetailsService detailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable)
                .cors(cors-> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("*");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    return config;
                }))
                .authorizeHttpRequests(req->
                        req.requestMatchers(WHITE_LIST)
                                .permitAll()
//                                .requestMatchers("").hasAnyRole(ADMIN.name(), MANAGER.name())
//                                .requestMatchers(GET,"").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
//                                .requestMatchers(POST, "").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
//                                .requestMatchers(PUT, "").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
//                                .requestMatchers(DELETE, "").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
//
//                                .requestMatchers("").hasRole(ADMIN.name())
//                                .requestMatchers(POST, "").hasAuthority(ADMIN_CREATE.name())
//                                .requestMatchers(GET, "").hasAuthority(ADMIN_READ.name())
//                                .requestMatchers(PUT, "").hasAuthority(ADMIN_UPDATE.name())
//                                .requestMatchers(DELETE, "").hasAuthority(ADMIN_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler())
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                );
        return http.build();
    }

    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth
                = new DaoAuthenticationProvider();
        auth.setUserDetailsService(detailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    private LogoutHandler logoutHandler(){
        return (request, response, authentication) -> {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            if(Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")){
                return;
            }
            jwt = authHeader.substring(7);
            TokenEntity tokenEntity = tokenRepository.findByToken(jwt)
                    .orElse(null);
            if(Objects.nonNull(tokenEntity)){

                tokenEntity.setExpired(true);
                tokenRepository.save(tokenEntity);
                SecurityContextHolder.clearContext();
            }
        };
    }



}
