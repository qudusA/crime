package org.fexisaf.crimerecordmanagementsystem.service.userService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.fexisaf.crimerecordmanagementsystem.model.LoginModel;
import org.fexisaf.crimerecordmanagementsystem.model.SignUpModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface UserServiceAuthentication {
    Ok<?> signUp(SignUpModel signUpModel, HttpServletRequest request);

    Ok<?> login(LoginModel loginModel) throws NotFoundException;

    Ok<?> verify(String token) throws NotFoundException;

    void sendEmail(String user, String emailVerification, String msg) throws MessagingException;

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    UserEntity changeUserRole(String user, Authentication connectedUser, Role role) throws NotFoundException;

   }
