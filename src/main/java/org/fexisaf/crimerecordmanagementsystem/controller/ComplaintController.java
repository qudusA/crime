package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.model.ComplaintModel;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.complaintService.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;


    @PostMapping("/complaint")
    public ResponseEntity<?> makeComplain(
            @RequestBody @Valid ComplaintModel complaintModel
//            ,@RequestBody("img") MultipartFile file
            ){

        Ok<?> res = complaintService.makeComplaint(complaintModel);

       return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @GetMapping("/highest-complaint")
    public ResponseEntity<?> highestComplaint(){

        Ok<?> res = complaintService.findHighestComplaint();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/all-complaint")
    public ResponseEntity<?> findAllComplaint(){
        Ok<?> res = complaintService.findAllComplaint();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/find-compliant-by-data")
    public ResponseEntity<?> findComplaintByData(
            @RequestParam String data
    ){
        Ok<?> res = complaintService.findComplaintByData(data);
        return ResponseEntity.ok(res);
    }








}
