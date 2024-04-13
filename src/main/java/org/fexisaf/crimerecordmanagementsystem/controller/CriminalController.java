package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.model.CriminalModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.criminalService.CriminalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','LAW_ENFORCEMENT_OFFICER')")
public class CriminalController {

    private final CriminalService criminalService;


//    Todo find a way to handle ExpiredJwtException
    @PreAuthorize("hasAnyAuthority('admin:create', 'police:create')")
    @PostMapping("/save-criminal/{crimeId}")
    public ResponseEntity<?> saveCriminal(@RequestPart("criminalData") @Valid CriminalModel criminalModel,
                                          @PathVariable Long crimeId
            ,@RequestParam(value = "criminalImage", required = false) MultipartFile imgFile

    )throws IOException{

                Ok<?> res = criminalService.saveCriminal(criminalModel,crimeId,imgFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @PreAuthorize("hasAnyAuthority('admin:read', 'police:read')")
    @GetMapping("/find-criminal/{criminalId}")
    public ResponseEntity<?> findCriminalById(@PathVariable("criminalId") Long criminalId){

        Ok<?> res = criminalService.findCriminalById(criminalId);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:read', 'police:read')")
    @GetMapping("/find-criminal-age/{age}")
    public ResponseEntity<?> findCriminalByAge(@PathVariable int age) throws IOException {

        Ok<?> res = criminalService.findCriminalByAge(age);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:read', 'police:read')")
    @GetMapping("/find-criminal-email/{email}")
    public ResponseEntity<?> findCriminalByEmail(@PathVariable String email) throws IOException {

        Ok<?> res = criminalService.findCriminalByEmail(email);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:read', 'police:read')")
    @GetMapping("/find-criminal-name-gender")
    public ResponseEntity<?> findCriminalByNameAndGender(@RequestParam("name") String name,
                                                 @RequestParam("gender") String gender) throws IOException {

        Ok<?> res = criminalService.findCriminalByNameAndGender(name, gender);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:delete', 'police:delete')")
    @DeleteMapping("/expunge-criminal/{criminalId}")
    public ResponseEntity<?> deleteById(@PathVariable Long criminalId) throws NotFoundException {

        Ok<?> res = criminalService.deleteById(criminalId);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:delete', 'police:delete')")
    @DeleteMapping("/expunge-criminal-by-name/{email}")
    public ResponseEntity<?> deleteCriminalEmail(@PathVariable String email){

        Ok<?> res = criminalService.deleteByCriminalName(email);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:update', 'police:update')")
    @PutMapping(value = "/change-criminal/{criminalId}")
    public ResponseEntity<?> updateCrimeById(
            @RequestPart(value = "file", required = false) MultipartFile file,
                                               @PathVariable Long criminalId
                                             ,@RequestBody(required = false) CriminalModel criminalModel
                                            ) throws IOException {


        Ok<?> res = criminalService.updateById(criminalModel, criminalId, file );

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }



}
