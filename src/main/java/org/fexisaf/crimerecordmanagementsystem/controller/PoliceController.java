package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPoliceStationEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.PoliceRanksEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.SuspectEntity;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfDepartmentModel;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfPoliceStationModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.policeService.PoliceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PoliceController {

    private final PoliceService policeStationService;

    @PostMapping("/create-police-station/{userId}")
    public ResponseEntity<?> createPoliceStation(
            @RequestBody @Valid ListOfPoliceStationModel stationModel
            ,@PathVariable("userId") Long id
            ) throws Exception {
        Ok<?> res = policeStationService.createPoliceStation(stationModel, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }

    @PostMapping("/create-police-rank")
    public ResponseEntity<?> createPoliceRanks(@RequestBody @Valid PoliceRanksEntity rank) throws Exception {
        Ok<?> res = policeStationService.createPoliceRank(rank);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/create-police-department")
    public ResponseEntity<?> createPoliceDepartment(
            @RequestBody @Valid ListOfDepartmentModel departmentEntity
    ) throws Exception {

        Ok<?> res = policeStationService.createPoliceDepartment(departmentEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/update-police-department/{deptId}")
    public ResponseEntity<?> updateDepartment(
            @RequestBody(required = false) @Valid ListOfDepartmentModel departmentEntity
            ,@PathVariable("deptId") Long id) throws Exception {
        Ok<?> res = policeStationService.updateDepartment(departmentEntity,id);
        return ResponseEntity.ok(res);
    }


    @PostMapping("/create-police-occupation/{deptId}")
    public ResponseEntity<?> createOccupation(@RequestParam("email") String email,
                                              @RequestParam("rank") String rank,
                                              @RequestParam("station") String station,
                                              @RequestParam("occupation") Role occupation,
                                              Authentication connectedUser,
                                              @PathVariable Long deptId
                                              ) throws NotFoundException, NotFoundException {
        Ok<?> res = policeStationService.createOccupation(email, rank, station, occupation, connectedUser, deptId);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/find-all-police-by-rank/{rankId}")
    public ResponseEntity<?> findByRank(@PathVariable("rankId") PoliceRanksEntity id){
        Ok<?> res = policeStationService.finByRank(id);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/find-all-police-by-station/{stationId}")
    public ResponseEntity<?> findByStation(@PathVariable("stationId") ListOfPoliceStationEntity id){
        Ok<?> res = policeStationService.finByStation(id);

        return ResponseEntity.ok(res);
    }



    @PostMapping("/appoint-police-head-of-department/{userId}/{deptId}")
    public ResponseEntity<?> appointHeadOfDepartment(@PathVariable Long userId,
                                                     @PathVariable Long deptId
                                                     ) throws Exception {
        Ok<?> res = policeStationService.appointHeadOfDepartment(userId,deptId );
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @PostMapping("/createSuspect/{caseId}")
    public ResponseEntity<?> createSuspect(@PathVariable Long caseId,
                                           @RequestBody @Valid SuspectEntity suspectEntity) throws Exception {
        Ok<?> res = policeStationService.createSuspect(caseId, suspectEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

}
