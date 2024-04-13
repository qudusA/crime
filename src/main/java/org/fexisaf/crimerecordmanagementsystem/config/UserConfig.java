package org.fexisaf.crimerecordmanagementsystem.config;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.repository.UserRepository;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class UserConfig {

    private final UserRepository userRepository;


    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username)
                    .orElseThrow(()->{
                        try {
                            throw new NotFoundException("user not found...");
                        } catch (NotFoundException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    });
    };


    @Bean
    public AuditorAware<Long> auditAware(){
        return new AuditAwareC();
    }


}

