package org.fexisaf.crimerecordmanagementsystem.service.changeDataService;

import jakarta.servlet.http.HttpServletRequest;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.model.ChangePasswordModel;
import org.fexisaf.crimerecordmanagementsystem.model.ForgetPasswordModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;

public interface ChangeDataService {
    Ok<?> forgetPassWord(String pass, String email, String otp) throws NotFoundException;

    Ok<?> changePassword(ChangePasswordModel passwordModel);

    Ok<?> sendOTP(ForgetPasswordModel pass, HttpServletRequest request) throws NotFoundException, NotFoundException;

    Ok<?> changeUserRole(Role role, String email) throws NotFoundException;

//    Ok changeOccupationOrPost(String occupation,
//                              String post, String email) throws NotFoundException;
}
