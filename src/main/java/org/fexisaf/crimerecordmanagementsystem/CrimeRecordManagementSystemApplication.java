package org.fexisaf.crimerecordmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
@EnableAspectJAutoProxy
@EnableScheduling
public class CrimeRecordManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrimeRecordManagementSystemApplication.class, args);
    }


}
