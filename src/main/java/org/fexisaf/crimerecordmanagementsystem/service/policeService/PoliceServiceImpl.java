package org.fexisaf.crimerecordmanagementsystem.service.policeService;

import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.controller.AuthenticationController;
import org.fexisaf.crimerecordmanagementsystem.entity.*;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfDepartmentModel;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfPoliceStationModel;
import org.fexisaf.crimerecordmanagementsystem.repository.*;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.userService.UserServiceAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PoliceServiceImpl implements PoliceService {


    private final UserRepository userRepository;
    private final UserServiceAuthentication userServiceAuthentication;
    private final CaseRepository caseRepository;
    private final AuthenticationController authenticationController;


    private final ListOfPoliceStationRepository policeStationRepository;
    private final PoliceRankRepository rankRepository;
    private final PoliceRepository policeRepository;
    private final ListOfPoliceDepartmentRepository departmentRepository;
    private final HeadOfDepartmentRepository headOfDepartmentRepository;
    private final SuspectRepository suspectRepository;


    @Override
    @Transactional
    public Ok<?> createPoliceStation(ListOfPoliceStationModel policeStationModel,
                                     Long userId) throws Exception {
        try {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found."));
            if (!user.getRole().equals(Role.LAW_ENFORCEMENT_OFFICER))
                throw new IllegalArgumentException("This user is not a police officer.");

            ListOfPoliceStationEntity policeStation = ListOfPoliceStationEntity
                    .builder()
                    .policeStationName(policeStationModel.getPoliceStationName())
                    .location(policeStationModel.getLocation())
                    .latitude(policeStationModel.getLatitude())
                    .longitude(policeStationModel.getLongitude())
                    .policeStationMaxStaffCapacity(policeStationModel.getPoliceStationMaxStaffCapacity())
                    .currentCapacity(policeStationModel.getCurrentCapacity())
                    .headOfPoliceStation(user)
                    .build();
            policeStationRepository.save(policeStation);

            var rank = rankRepository.findByRank("Superintendent of Police (SP)").orElseThrow();
            var dept = departmentRepository.findByDepartment("Operations department").orElseThrow();

            police(rank.getRank(), policeStation.getPoliceStationName(), user, dept.getId());
            appointHeadOfDepartment(userId, dept.getId());
            return Ok.builder()
                    .message("Police station creation successful.")
                    .statusName(HttpStatus.CREATED.name())
                    .statusCode(HttpStatus.CREATED.value())
                    .date(LocalDateTime.now())
                    .build();
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        } catch (ExpiredJwtException e){
            System.out.println("herer   e   e");
            return Ok.builder()
                    .message("Session expired. Please log in again.")
                    .statusName(HttpStatus.UNAUTHORIZED.name())
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .date(LocalDateTime.now())
                    .build();
        } catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }



    @Override
    @Transactional
    public Ok<?> createPoliceRank(PoliceRanksEntity rank) throws Exception {
        try {

            PoliceRanksEntity policeRank = PoliceRanksEntity.builder()
                    .rank(rank.getRank())
                    .build();
            rankRepository.save(policeRank);
            return Ok.builder()
                    .date(LocalDateTime.now())
                    .statusCode(HttpStatus.CREATED.value())
                    .statusName(HttpStatus.CREATED.name())
                    .message("rank creation successful..")
                    .build();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    @Override
    @Transactional
    public Ok<?> createOccupation(String email, String rank,
                                  String station, Role occupation,
                                  Authentication connectedUser, Long deptId) throws NotFoundException {
        try {

          UserEntity savedUser = userServiceAuthentication.changeUserRole(email, connectedUser,
                    occupation);

            if (Objects.requireNonNull(savedUser.getRole()) == Role.LAW_ENFORCEMENT_OFFICER) {
                police(rank, station, savedUser, deptId);
            } else {
                throw new NotFoundException("invalid entry");
            }
            return Ok.builder()
                    .message("occupation creation successful...")
                    .statusName(HttpStatus.CREATED.name())
                    .statusCode(HttpStatus.CREATED.value())
                    .date(LocalDateTime.now())
                    .build();
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Ok<?> finByRank(PoliceRanksEntity id) {
        List<PoliceWardenJudgeEntity> res = policeRepository.findAllByPoliceRanks(id);
        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message(res)
                .build();
    }

    @Override
    public Ok<?> finByStation(ListOfPoliceStationEntity id) {
        List<PoliceWardenJudgeEntity> res = policeRepository.findAllByListOfPoliceStation(id);

        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message(res)
                .build();
    }

    @Override
    @Transactional
    public Ok<?> createPoliceDepartment(
            ListOfDepartmentModel departmentEntity) throws Exception {
try {

    var department = ListOfPoliceDepartmentEntity.builder()
            .department(departmentEntity.getDepartment())
            .build();
    departmentRepository.save(department);
    return Ok.builder()
            .statusName(HttpStatus.CREATED.name())
            .statusCode(HttpStatus.CREATED.value())
            .date(LocalDateTime.now())
            .message("department created...")
            .build();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Ok<?> updateDepartment(
            ListOfDepartmentModel departmentEntity,
            Long deptId) throws Exception {
        var dept = departmentRepository.findById(deptId)
                .orElseThrow(()-> new NotFoundException("department not found..."));

        if(Objects.nonNull(departmentEntity)){
            dept.setDepartment(departmentEntity.getDepartment());
        }
        try {

        departmentRepository.save(dept);
        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message("update successful...")
                .build();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Ok<?> appointHeadOfDepartment(Long userId, Long deptId) throws Exception {
        var dept = departmentRepository.findById(deptId)
                .orElseThrow(()-> new NotFoundException("department not found..."));

        var police = policeRepository.findByUserEntity(userId)
                    .orElseThrow(()-> new NotFoundException("invalid staff..."));
        if(!police.getDepartmentId().getId().equals(deptId)){
            police.setDepartmentId(dept);
            policeRepository.save(police);
        }
        if(!(police.getUserEntity().getRole().equals(Role.LAW_ENFORCEMENT_OFFICER))) throw new IllegalArgumentException("this personnel is not a police officer");
        var head = HeadOfDepartmentEntity.builder()
                .department(dept)
                .policeStation(police.getListOfPoliceStation())
                .userId(police.getUserEntity())
                .build();
        try {

            headOfDepartmentRepository.save(head);

            return Ok.builder()
                    .statusName(HttpStatus.CREATED.name())
                    .statusCode(HttpStatus.OK.value())
                    .date(LocalDateTime.now())
                    .message("head of department saved...")
                    .build();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Ok<?> createSuspect(Long caseId, SuspectEntity suspectEntity) throws Exception {
        try {
       var case1 = caseRepository.findById(caseId)
               .orElseThrow(()-> new NotFoundException("case not found..."));
       List<CaseEntity> caseEntityList = List.of(case1);
      var suspectEntity2 = suspectRepository.findSuspectEntitiesByDateOfBirthAndFirstNameAndLastName(suspectEntity.getDateOfBirth(),
               suspectEntity.getFirstName(), suspectEntity.getLastName());
        SuspectEntity suspectEntity1 = null;

      if(suspectEntity2.isEmpty()) {
            suspectEntity1 = SuspectEntity.builder()
                   .address(suspectEntity.getAddress())
                   .dateOfBirth(suspectEntity.getDateOfBirth())
                   .firstName(suspectEntity.getFirstName())
                   .lastName(suspectEntity.getLastName())
                   .phoneNumber(suspectEntity.getPhoneNumber())
                   .caseEntities(caseEntityList)
                   .build();
          suspectRepository.save(suspectEntity1);
       }else {
          SuspectEntity existingSuspect = suspectEntity2.get();
          if (existingSuspect.getCaseEntities().contains(case1)) {
              return Ok.builder()
                      .statusCode(HttpStatus.BAD_REQUEST.value())
                      .statusName(HttpStatus.BAD_REQUEST.name())
                      .date(LocalDateTime.now())
                      .message("case already opened...")
                      .build();
          }

          existingSuspect.getCaseEntities().add(case1);
          suspectRepository.save(existingSuspect);

      }

           return Ok.builder()
                   .statusCode(HttpStatus.CREATED.value())
                   .statusName(HttpStatus.CREATED.name())
                   .date(LocalDateTime.now())
                   .message("suspect creation successful...")
                   .build();
       }catch (Exception e){
           throw new Exception(e.getMessage());
       }
    }

    private void police(String rank, String station, UserEntity user, Long deptId) throws NotFoundException {
        var foundRank = rankRepository.findByRank(rank)
                .orElseThrow(() -> new NotFoundException("rank not found"));
        var foundPoliceStation = policeStationRepository.findByPoliceStationName(station)
                .orElseThrow(() -> new NotFoundException("police station not found..."));
        var department = departmentRepository.findById(deptId)
                .orElseThrow(()-> new NotFoundException("department not found..."));
        var currentCapacity = foundPoliceStation.getCurrentCapacity() + 1;
        if (currentCapacity > 100)
            throw new IllegalArgumentException("the station has reached it max staff capacity");
        foundPoliceStation.setCurrentCapacity(currentCapacity);

        var policeWardenJudge = PoliceWardenJudgeEntity.builder()
                .userEntity(user)
                .listOfPoliceStation(foundPoliceStation)
                .policeRanks(foundRank)
                .departmentId(department)
                .build();
        policeRepository.save(policeWardenJudge);
    }
}
