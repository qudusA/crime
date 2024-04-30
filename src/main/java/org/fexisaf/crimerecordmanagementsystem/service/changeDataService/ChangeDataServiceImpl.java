package org.fexisaf.crimerecordmanagementsystem.service.changeDataService;

import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.config.SmsConfig;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.TokenEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.fexisaf.crimerecordmanagementsystem.model.ChangePasswordModel;
import org.fexisaf.crimerecordmanagementsystem.model.ForgetPasswordModel;
import org.fexisaf.crimerecordmanagementsystem.repository.TokenRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.UserRepository;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Random;




@Transactional(rollbackFor = ApiException.class)
@Service
@RequiredArgsConstructor
public class ChangeDataServiceImpl implements ChangeDataService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmsConfig smsConfig;
    private final TokenRepository tokenRepository;

//    @Value("${token.expiration}")
//    private LocalDateTime expiration;


    @Override
    public Ok<?> forgetPassWord(String pass, String email, String otp) throws NotFoundException {
        try {
            TokenEntity foundOtp = tokenRepository.findByToken(otp)
                    .orElseThrow(() -> new NotFoundException("invalid input otp."));
            if (foundOtp.isExpired() || foundOtp.getExpirationTime().isBefore(LocalDateTime.now())) {
                throw new JwtException("expired otp");
            }
            UserEntity userEntity = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("email not found."));
            userEntity.setPassword(passwordEncoder.encode(pass));

            foundOtp.setExpired(true);
            tokenRepository.save(foundOtp);
            userRepository.save(userEntity);
            return Ok.builder()
                    .message("password reset successful...")
                    .statusName(HttpStatus.OK.name())
                    .statusCode(HttpStatus.OK.value())
                    .date(LocalDateTime.now())
                    .build();
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
    }


    //TODO send sms to user as well
    @Override
    @Transactional
    public Ok<?> changePassword(@Valid @RequestBody ChangePasswordModel passwordModel) {
        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();


        if (!passwordModel.getPassword().equals(passwordModel.getConfirmPassword())){
            throw new IllegalStateException("Password are not the same");
        }

        if(!passwordEncoder.matches(passwordModel.getCurrentPassword(), userEntity.getPassword())){
            throw new IllegalStateException("Wrong password");
        }



        userEntity.setPassword(passwordEncoder.encode(passwordModel.getPassword()));
        userRepository.save(userEntity);


        return Ok.builder()
                .message("password successfully changed...")
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .build();
    }

    //    TODO exception handling the transactional did not work
    @Transactional
    @Override
    public Ok<?> sendOTP(@Valid @RequestBody ForgetPasswordModel pass, HttpServletRequest request) throws NotFoundException {
        try {

            if (!pass.getPassword().equals(pass.getConfirmPassword())){
                throw new IllegalStateException("Password are not the same");
            }

            String email = pass.getEmail();

            UserEntity userEntity = userRepository.findByEmail(email)
                    .orElseThrow(()-> new NotFoundException("data not found."));
            PhoneNumber to = new PhoneNumber(userEntity.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(smsConfig.getPhoneNumber());
            MessageCreator creator = Message.creator(to, from, getMessage(userEntity));
            creator.create();

            return Ok.builder()
                    .message("otp has been sent to the registered number" +
                            "redirect:" + getServer(request)+ "/forgetpassword/inputOtp?pass=" + pass.getPassword() + "&email=" + pass.getEmail())
                    .date(LocalDateTime.now())
                    .statusCode(HttpStatus.OK.value())
                    .statusName(HttpStatus.OK.name())
                    .build();
        }catch (ApiException e){
            throw new ApiException(e.getMessage());
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Ok<?> changeUserRole(Role role, String email) throws NotFoundException {

       var user = userRepository.findByEmail(email)
               .orElseThrow(()-> new NotFoundException("user not found..."));

       user.setRole(role);
       userRepository.save(user);

        return Ok.builder()
                .message("role change successful...")
                .date(LocalDateTime.now())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .build();
    }
//
//    @Override
//    public Ok changeOccupationOrPost(String occupation,
//                                     String post,
//                                     String email) throws NotFoundException {
//        UserEntity userEntity =
//                userRepository.findByEmail(email).orElseThrow(
//                        ()-> new NotFoundException("invalid email address"));
//        if(!userEntity.getOccupation().isBlank()){
//            userEntity.setOccupation(occupation);
//        }
//
////        if(!userEntity.getPost().isBlank()){
////            userEntity.setPost(post);
////        }
//        userRepository.save(userEntity);
//        return Ok.builder()
//                .OkTime(LocalDateTime.now())
//                .status(HttpStatus.OK.value())
//                .statusName(HttpStatus.OK.name())
//                .message("update successful...")
//                .build();
//    }
//

    private String getMessage(UserEntity userEntity) {
        int otp = getOtp(userEntity);

        return """
                                   REQUEST FOR CHANGE OF PASSWORD
                            
                    you request for change of password on this app.we want to make sure its really you.
                    please input the opt below, this code is valid for 10 minutes. if
                    you do not want to make any changes, you can ignore this message.
                
                """ + otp;
    }

    private int getOtp(UserEntity userEntity) {
        Random random = new Random();
        double randGen = random.nextDouble();
        int otp = (int) (randGen * 1000_000);
        System.out.println(otp);
        TokenEntity tokenEntity = TokenEntity.builder()
                .token(String.valueOf(otp))
                .userEntity(userEntity)
                .isExpired(false)
                .expirationTime(LocalDateTime.now().plusMinutes(10))
                .build();

        tokenRepository.save(tokenEntity);
        return otp;
    }

    private String getServer(HttpServletRequest request) {
        return "http://" + request.getServerName()+":"
                + request.getServerPort()
                +request.getContextPath();
    }

}
