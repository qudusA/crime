package org.fexisaf.crimerecordmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.auditService.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {

    private final AuditService auditService;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/all-audit")
    public ResponseEntity<?> getAllAudit(){

        Ok<?> res = auditService.getAllAudit();
        return ResponseEntity.ok(res);
    }


    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/get-by-date")
    public ResponseEntity<?> getByDate(@RequestParam("date")LocalDateTime localDateTime){

        Ok<?> res = auditService.findByDate(localDateTime);

        return ResponseEntity.ok(res);
    }




}
