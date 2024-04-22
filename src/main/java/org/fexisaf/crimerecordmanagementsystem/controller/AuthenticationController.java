package org.fexisaf.crimerecordmanagementsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.model.LoginModel;
import org.fexisaf.crimerecordmanagementsystem.model.SignUpModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.userService.UserServiceAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserServiceAuthentication authService;

    @PostMapping("/signup")
    public ResponseEntity<Ok<?>> signUp(@RequestBody @Valid SignUpModel signUpModel,
                                     HttpServletRequest request) {
        Ok<?> res = authService.signUp(signUpModel, request);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/")
    public ResponseEntity<?> home(){

        Map<String, String> map = new HashMap<>();
        map.put("author", "OLANREWAJU QUDUS ABIODUN");
        map.put("message","connected successful");

        return ResponseEntity.ok(map);
    }

    @PostMapping("/login")
    public ResponseEntity<Ok<?>> login(@RequestBody LoginModel loginModel) throws NotFoundException {
        Ok<?> res = authService.login(loginModel);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token ) throws NotFoundException, NotFoundException {
        Ok<?> res = authService.verify(token);

        return ResponseEntity.ok(res);
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }



    @PostMapping("/signup-by-admin")
    public ResponseEntity<Ok<?>> signUpByAdmin(
            @RequestBody @Valid SignUpModel signUpModel,
                                        HttpServletRequest request) {
        Ok<?> res = authService.signUp(signUpModel, request);
        return ResponseEntity.ok(res);
    }




}
