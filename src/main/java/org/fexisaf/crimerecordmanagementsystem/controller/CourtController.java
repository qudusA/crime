package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.ListOfCourtRoomsEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfCourtHouseModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.chargedCaseService.ChargedCaseService;
import org.fexisaf.crimerecordmanagementsystem.service.courtService.CourtService;
import org.fexisaf.crimerecordmanagementsystem.service.policeService.PoliceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('JUDGE','ADMIN')")
public class CourtController {

    private final PoliceService policeStationService;
    private final CourtService courtService;
    private final ChargedCaseService chargedCaseService;

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/create-court-House")
    public ResponseEntity<?> createPoliceStation(
            @RequestBody @Valid ListOfCourtHouseModel courtHouseModel
//            ,@PathVariable("userId") Long id
            ){
        Ok<?> res = courtService.createCourtHouse(courtHouseModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/create-court-room")
    public ResponseEntity<?> createPoliceRanks(@RequestBody @Valid ListOfCourtRoomsEntity listOfCourtRooms) {
        Ok<?> res = courtService.createCourtRoom(listOfCourtRooms);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/create-occupation/{roomId}")
    public ResponseEntity<?> createOccupation(@RequestParam("email") String email,
                                              @RequestParam("courtHouse") String courtHouse,
                                              @RequestParam("occupation") Role occupation,
                                              Authentication connectedUser,
                                              @PathVariable Long roomId
                                              ) throws NotFoundException, NotFoundException {
        Ok<?> res = courtService.createOccupation(email, courtHouse, occupation, connectedUser, roomId);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/all-court-house")
    public ResponseEntity<?> findAllCourtHouse(){

        Ok<?> res = courtService.findAllCourtHouse();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }


// TODO assign case to judge

    @PostMapping("/assign-case-to-judge/{roomId}/{caseId}")
    public ResponseEntity<?> assignCaseToJudge(@PathVariable Long roomId,
                                               @PathVariable Long caseId) throws NotFoundException {
        Ok<?> res = courtService.assignCaseToJudge(roomId, caseId);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }

}
