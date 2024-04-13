package org.fexisaf.crimerecordmanagementsystem.service.auditService;

import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;

import java.time.LocalDateTime;

public interface AuditService {
    Ok<?> getAllAudit();

    Ok<?> findByDate(LocalDateTime localDateTime);
}
