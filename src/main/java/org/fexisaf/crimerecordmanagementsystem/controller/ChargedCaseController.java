package org.fexisaf.crimerecordmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.chargedCaseService.ChargedCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('LAW_ENFORCEMENT_OFFICER','ADMIN')")
public class ChargedCaseController {
    private final ChargedCaseService chargedCaseService;




    @GetMapping("/get-all-judge")
    public ResponseEntity<?> findAllJudge(){

        Ok<?> res = chargedCaseService.findAllJudge();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/charged-case/{caseId}")
    public ResponseEntity<?> chargedCase(@PathVariable Long caseId
            ,@RequestParam String courtHouseName
                                         ) throws NotFoundException {
        Ok<?> res = chargedCaseService.createChargedCase(caseId,courtHouseName);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }





}
