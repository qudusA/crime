package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.fexisaf.crimerecordmanagementsystem.model.CrimeModel;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.crimeService.CrimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('LAW_ENFORCEMENT_OFFICER')")
public class CrimeController {


    private final CrimeService crimeService;

    @PostMapping("/report-crime")
    public ResponseEntity<?> crimeReport(@RequestPart("data") @Valid CrimeModel crimeModel,
                                         @RequestParam(value = "image", required = false) MultipartFile imgFile,
                                         @RequestParam(value = "video", required = false) MultipartFile videoFile
                                         ) throws IOException {
        Ok<?> res = crimeService.reportCrime(crimeModel,
                imgFile, videoFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/find-crime/{crimeId}")
    public ResponseEntity<?> getCrimeById(@PathVariable Long crimeId ) throws IOException {
        Ok<?> res = crimeService.getCrimeById(crimeId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/all-crime-by-date/{crimeDate}")
    public ResponseEntity<?> getAllCrimeByCrimeDate(@PathVariable String  crimeDate){
        Ok<?> res = crimeService.getAllByCrimeDate(crimeDate);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/all-crime-by-location/{crimeLocation}")
    public ResponseEntity<?> getAllCrimeByCrimeLocation(@PathVariable String  crimeLocation){
        Ok<?> res = crimeService.getAllCrimeByCrimeLocation(crimeLocation);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/all-crime-by-crime-type/{crimeType}")
    public ResponseEntity<?> getAllCrimeByCrimeType(@PathVariable String  crimeType){
        Ok<?> res = crimeService.getAllCrimeByCrimeType(crimeType);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/all-crime-by-investigator/{investigator}")
    public ResponseEntity<?> getAllCrimeByInvestigator(@PathVariable UserEntity investigator){
        Ok<?> res = crimeService.getAllCrimeByInvestigator(investigator);
        return ResponseEntity.ok(res);
    }


    @GetMapping("/all-crime")
    public ResponseEntity<?> getAllCrimeReport(){
        Ok<?> res =crimeService.getAllCrimeReport();
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/expunge-crime/{crimeId}")
    public ResponseEntity<?> deleteById(@PathVariable Long crimeId){

        Ok<?> res = crimeService.deleteById(crimeId);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @PutMapping("/update-crime-report/{crimeId}")
    public ResponseEntity<?> updateCrimeById(@PathVariable Long crimeId,
                                             @RequestBody CrimeModel crimeModel,
                                             @RequestParam(value = "image", required = false) MultipartFile imgFile,
                                             @RequestParam(value = "video", required = false) MultipartFile videoFile
                                            ) throws IOException {

        Ok<?> res = crimeService.updateById(crimeModel, crimeId, imgFile, videoFile);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }





}
