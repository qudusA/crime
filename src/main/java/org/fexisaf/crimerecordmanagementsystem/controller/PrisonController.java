package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPrisonFacility;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.WardenRanksEntity;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfDepartmentModel;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfPrisonModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.prisonService.PrisonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('LAW_ENFORCEMENT_OFFICER','ADMIN')")
public class PrisonController {

private final PrisonService prisonService;



    @PostMapping("/create-prison-facility/{userId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createPrisonFacility(
            @RequestBody @Valid ListOfPrisonModel stationModel,
            @PathVariable("userId") Long userId
            ) throws Exception {
        Ok<?> res = prisonService.createPrisonFacility(stationModel, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }

    @PostMapping("/create-prison-rank")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createPrisonRanks(@RequestBody @Valid WardenRanksEntity rank){
        Ok<?> res = prisonService.createWardenRank(rank);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @PostMapping("/create-prison-department")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createPoliceDepartment(
            @RequestBody @Valid ListOfDepartmentModel departmentEntity
    ) throws NotFoundException {

        Ok<?> res = prisonService.createPrisonDepartment(departmentEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/update-prison-department/{deptId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> updateDepartment(
            @RequestBody(required = false) @Valid ListOfDepartmentModel departmentEntity
            ,@PathVariable("deptId") Long id) throws NotFoundException {
        Ok<?> res = prisonService.updatePrisonDepartment(departmentEntity,id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/create-prison-occupation/{deptId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createOccupation(@RequestParam("email") String email,
                                              @RequestParam("rank") String rank,
                                              @RequestParam("station") String station,
                                              @RequestParam("occupation") Role occupation,
                                              Authentication connectedUser,
                                              @PathVariable Long deptId
                                              ) throws NotFoundException, NotFoundException {
        Ok<?> res = prisonService
                .createOccupation(email, rank, station, occupation, connectedUser, deptId);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/find-all-by-prison-rank/{rankId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> findByRank(@PathVariable WardenRanksEntity rankId){
        Ok<?> res = prisonService.finByPrisonRank(rankId);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/find-all-by-prison-facility/{facilityId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> findByPrisonFacility(@PathVariable ListOfPrisonFacility facilityId){
        Ok<?> res = prisonService.finByPrisonFacility(facilityId);

        return ResponseEntity.ok(res);
    }


    @PostMapping("/appoint-head-of-prison-department/{userId}/{deptId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> appointHeadOfDepartment(@PathVariable Long userId,
                                                     @PathVariable Long deptId
    ) throws NotFoundException {
        Ok<?> res = prisonService.appointHeadOfPrisonDepartment(userId,deptId );
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }



}
