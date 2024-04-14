package org.fexisaf.crimerecordmanagementsystem;

import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.fexisaf.crimerecordmanagementsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
@EnableAspectJAutoProxy
//@EnableScheduling
public class CrimeRecordManagementSystemApplication {



    public static void main(String[] args) {
        SpringApplication.run(CrimeRecordManagementSystemApplication.class, args);
    }


}
