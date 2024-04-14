package org.fexisaf.crimerecordmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.model.PunishmentModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.punishmentService.PunishmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','JUDGE')")
public class PunishmentController {

    private final PunishmentService punishmentService;


//    Todo i want to send email to investigation officer in charge to
//     verify if the punishment has been completed i.e comunity service ;


    @PreAuthorize("hasAnyAuthority('admin:create', 'judge:create')")
    @PostMapping("/save-punishment/{chargedCaseId}")
    public ResponseEntity<?> savePunishment(@RequestBody PunishmentModel punishmentModel,
                                            @PathVariable Long chargedCaseId) throws Exception {
        Ok<?> res = punishmentService.savePunishment(punishmentModel, chargedCaseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:read', 'judge:read')")
    @GetMapping("/find-all-by-punishment-type/{type}")
    public ResponseEntity<?> findByPunishmentType(@PathVariable String type) throws NotFoundException {

        Ok<?> res = punishmentService.findByPunishmentType(type);

        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:delete', 'judge:delete')")
    @DeleteMapping("/expunge-punishment/{punishmentId}")
    public ResponseEntity<?> deleteById(@PathVariable Long punishmentId) throws Exception {

        Ok<?> res = punishmentService.deleteById(punishmentId);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PreAuthorize("hasAnyAuthority('admin:update', 'judge:update')")
    @PutMapping("/change-punishment/{punishmentId}")
    public ResponseEntity<?> updateCrimeById(@PathVariable Long punishmentId,
                                             @RequestBody PunishmentModel punishmentModel) throws Exception {

        Ok<?> res = punishmentService.updateById(punishmentModel, punishmentId);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
