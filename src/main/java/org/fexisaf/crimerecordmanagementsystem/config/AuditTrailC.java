package org.fexisaf.crimerecordmanagementsystem.config;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.fexisaf.crimerecordmanagementsystem.entity.AuditTrailEntity;
import org.fexisaf.crimerecordmanagementsystem.repository.AuditTrailRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditTrailC {

    private final AuditTrailRepository auditTrailRepository;

    @AfterReturning("execution(* org.fexisaf.crimerecordmanagementsystem.service..*.*(..))")
    public void logAuditTrail(JoinPoint joinPoint) {

           Authentication authentication = SecurityContextHolder.getContext()
                   .getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
//        UserEntity userId =(UserEntity) authentication.getPrincipal();
           String username = authentication.getName();
           String action = joinPoint.getSignature().getName();

           String details = "User " + username + " invoked method " + action;

           AuditTrailEntity auditTrail = AuditTrailEntity.builder()
                   .userName(username)
                   .action(action)
                   .timeStamp(LocalDateTime.now())
                   .details(details)
                   .build();

           auditTrailRepository.save(auditTrail);
       }
    }
}
