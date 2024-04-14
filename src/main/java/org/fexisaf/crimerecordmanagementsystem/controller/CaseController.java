package org.fexisaf.crimerecordmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.caseService.CaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @PostMapping("/open-case/{complaintId}")
    public ResponseEntity<?> openCase(@PathVariable Long complaintId) throws NotFoundException {

        Ok<?> res = caseService.saveComplaintToCaseByUser(complaintId);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
