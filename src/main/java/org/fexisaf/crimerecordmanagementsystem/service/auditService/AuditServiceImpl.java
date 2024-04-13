package org.fexisaf.crimerecordmanagementsystem.service.auditService;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.AuditTrailEntity;
import org.fexisaf.crimerecordmanagementsystem.repository.AuditTrailRepository;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditTrailRepository auditTrailRepository;

    @Override
    public Ok<?> getAllAudit() {

       var res = auditTrailRepository.findAll();


        return Ok.builder()
                .message(res)
                .date(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .build();
    }

    @Override
    public Ok<?> findByDate(LocalDateTime localDateTime) {
        List<AuditTrailEntity> results = auditTrailRepository.findByTimeStamp(localDateTime);

        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message(results)
                .date(LocalDateTime.now())
                .build();
    }
}
