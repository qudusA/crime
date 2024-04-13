package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.model.ChangePasswordModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.changeDataService.ChangeDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ChangeDataController {
    private final ChangeDataService changeDatService;

    @PutMapping("/forget-password")
    public ResponseEntity<?> forgetPassWord(@RequestBody @Valid ChangePasswordModel pass) throws NotFoundException {
        Ok<?> res = changeDatService.sendOTP(pass);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/forget-password/inputOtp")
    public ResponseEntity<?> forget(@RequestParam("pass") String password,
                                    @RequestParam("email") String email,
                                    @RequestParam("otp") String otp) throws NotFoundException {
        Ok<?> res = changeDatService.forgetPassWord(password, email, otp);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid
                                            ChangePasswordModel passwordModel){
        Ok<?> res = changeDatService.changePassword(passwordModel);
        return ResponseEntity.ok(res);
    }


}
