package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.OpenCaseOnMatch;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.openCaseOnMatctService.OpenCaseOnMatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenCaseOnMatchController {

    private final OpenCaseOnMatchService openCaseOnMatchService;


    @PostMapping("/save-to-match")
    public ResponseEntity<?> save(@RequestBody @Valid OpenCaseOnMatch onMatch){

        Ok<?> res = openCaseOnMatchService.saveData(onMatch);

        return ResponseEntity.ok(res);
    }

}
