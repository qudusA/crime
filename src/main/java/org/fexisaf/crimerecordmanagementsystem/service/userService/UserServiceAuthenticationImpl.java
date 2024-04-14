package org.fexisaf.crimerecordmanagementsystem.service.userService;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.UnexpectedTypeException;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.TokenEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.fexisaf.crimerecordmanagementsystem.event.emailEvent.EventObj;
import org.fexisaf.crimerecordmanagementsystem.model.LoginModel;
import org.fexisaf.crimerecordmanagementsystem.model.SignUpModel;
import org.fexisaf.crimerecordmanagementsystem.model.TokenModel;
import org.fexisaf.crimerecordmanagementsystem.repository.TokenRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.UserRepository;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.jwtService.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceAuthenticationImpl implements UserServiceAuthentication {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final JavaMailSender javaMailSender;
    private final ApplicationEventPublisher publisher;


    @Value("${app.sendfrom}")
    private String FROM;



    @Override
//    @Transactional
    public Ok<?> signUp(SignUpModel signUpModel, HttpServletRequest request) {
        try {
                Authentication authentication = SecurityContextHolder.getContext()
                        .getAuthentication();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date =String.valueOf(LocalDate.
                    parse(signUpModel.getDateOfBirth(), formatter));

            String firstName = capitalizeName(signUpModel.getFirstName());
            String lastName = capitalizeName(signUpModel.getLastName());
            if(Arrays.asList(Role.ADMIN, Role.CLARK, Role.LAW_ENFORCEMENT_OFFICER, Role.WARDEN)
                    .contains(signUpModel.getRole()) && (Objects.isNull(authentication)
                    || !authentication.isAuthenticated()
                    || authentication instanceof AnonymousAuthenticationToken)){
                throw new IllegalArgumentException("unAuthorized task...");
            }else if (!(authentication instanceof AnonymousAuthenticationToken) && !((UserEntity)authentication.getPrincipal()).getRole().equals(Role.ADMIN)) {
                throw new IllegalArgumentException("Only ADMIN users can perform this action.");
            }

            String phoneNumber = signUpModel.getPhoneNumber().startsWith("+")
            ? signUpModel.getPhoneNumber() : "+234".concat(signUpModel.getPhoneNumber().substring(1));

            UserEntity user = UserEntity.builder()
                    .email(signUpModel.getEmail())
                    .password(passwordEncoder.encode(signUpModel.getPassword()))
                    .firstName(firstName)
                    .lastName(lastName)
                    .dateOfBirth(date)
                    .phoneNumber(phoneNumber)
                    .role(signUpModel.getRole())
                    .address(signUpModel.getAddress())
                    .build();
            var userRes = userRepository.save(user);
            System.out.println(userRes);
            var tokenEntity =  getTokenEntity(user);
            tokenRepository.save(tokenEntity);

            publisher.publishEvent(new EventObj(tokenEntity, getServer(request)));

            return Ok.builder()
                    .message("kindly check the registered mail for verification.")
                    .statusCode(HttpStatus.CREATED.value())
                    .statusName(HttpStatus.CREATED.name())
                    .date(LocalDateTime.now())
                    .build();
        }catch (DateTimeException e){
            throw new DateTimeException("invalid input date...");
        }catch (UnexpectedTypeException e){
            throw new UnexpectedTypeException("invalid input at"+ e.getMessage()
                    .substring(e.getMessage().lastIndexOf(" ")));
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("user already exist...");
        }
    }

    @Override
    public Ok<?> login(LoginModel loginModel) throws NotFoundException {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginModel.getEmail(),
                            loginModel.getPassword()
                    )
            );

            UserEntity user = userRepository.findByEmail(loginModel.getEmail())
                    .orElseThrow(() -> new NotFoundException("email or password not found."));
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            var listOfToken = tokenRepository.findAllById(user.getId());
            listOfToken.forEach(e -> e.setExpired(true));

            var token = TokenModel.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
            var tokenEntity = TokenEntity.builder()
                    .userEntity(user)
                    .token(jwtToken)
                    .isExpired(false)
                    .expirationTime(LocalDateTime.now().plusHours(1))
                    .build();
            tokenRepository.save(tokenEntity);
            return Ok.builder()
                    .message(token)
                    .date(LocalDateTime.now())
                    .statusCode(HttpStatus.OK.value())
                    .statusName(HttpStatus.OK.name())
                    .build();
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("invalid username or password...");
        }catch (AuthenticationServiceException e){
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    public Ok<?> verify(String token) throws NotFoundException {
        try {

            TokenEntity foundToken = tokenRepository.findByToken(token)
                    .orElseThrow(()-> new NotFoundException("invalid...."));
            if(foundToken.isExpired() || foundToken.getExpirationTime()
                    .isBefore(LocalDateTime.now())) throw new JwtException("invalid token...");
            String email = jwtService.extractUserName(token);
            UserEntity userEntity = userRepository.findByEmail(email).
                    orElseThrow(()-> new NotFoundException("verification failed..."));

            if (jwtService.isTokenExpired(userEntity, token)) {
                userEntity.setVerified(true);
                foundToken.setExpired(true);
                userRepository.save(userEntity);
                tokenRepository.save(foundToken);
            }
            return Ok.builder()
                    .message("verification successful."+ " /login")
                    .statusName(HttpStatus.OK.name())
                    .statusCode(HttpStatus.OK.value())
                    .date(LocalDateTime.now())
                    .build();
        }catch (JwtException  e){
            throw new JwtException(e.getMessage());
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }


    }

    @Override
    public void sendEmail(String user, String emailVerification, String msg) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(user);
            helper.setSubject(emailVerification);
            helper.setText(msg);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MessagingException(e.getMessage());
        }
    }


    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenExpired(user,refreshToken )) {
                var accessToken = jwtService.generateToken(user);
                var listOfToken = tokenRepository.findAllById(user.getId());
                listOfToken.forEach(e -> e.setExpired(true));
                var tok =  getTokenEntity(user);
                tokenRepository.save(tok);
                var authResponse = TokenModel.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public UserEntity changeUserRole(String email,
                                     Authentication connectedUser,
                                     Role role
    ) throws NotFoundException {
        UserEntity principal =(UserEntity)  connectedUser.getPrincipal();
        if(!principal.getRole().equals(Role.ADMIN))
            throw new IllegalArgumentException("unAuthorized ...");

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user not found..."));

        user.setRole(role);
       return userRepository.save(user);

    }

    private static String capitalizeName(String name) {
        String firstLetter = name.trim().substring(0,1).toUpperCase();
        String other = name.trim().substring(1).toLowerCase();
        return firstLetter.concat(other);
    }



    private TokenEntity getTokenEntity(UserEntity user) {
        var token = jwtService.generateSingUpToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
        return TokenEntity.builder()
                .token(token)
                .expirationTime(LocalDateTime.now().plusMinutes(60))
                .userEntity(user)
                .build();

    }


    private String getServer(HttpServletRequest request) {
        return "http://" + request.getServerName()+":"
                + request.getServerPort()
                +request.getContextPath();
    }


}
