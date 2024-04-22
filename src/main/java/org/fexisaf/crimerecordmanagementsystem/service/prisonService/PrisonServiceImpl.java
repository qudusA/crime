package org.fexisaf.crimerecordmanagementsystem.service.prisonService;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.*;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfDepartmentModel;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfPrisonModel;
import org.fexisaf.crimerecordmanagementsystem.repository.*;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.userService.UserServiceAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PrisonServiceImpl implements PrisonService{

    private final UserRepository userRepository;
    private final UserServiceAuthentication userServiceAuthentication;


    private final ListOfPrisonFacilityRepository listOfPrisonFacility;
    private final PrisonWardenRankRepository prisonWardenRankRepository;
    private final PrisonRepository prisonRepository;
    private final ListOfPrisonDepartmentRepository listOfPrisonDepartmentRepository;
    private final HeadOfPrisonDepartmentRepository headOfPrisonDepartmentRepository;

    @Override
    @Transactional
    public Ok<?> createPrisonFacility(ListOfPrisonModel stationModel, Long userId) throws Exception {
        try {
        var foundUser = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("user not found..."));


        ListOfPrisonFacility prisonFacility = ListOfPrisonFacility
                .builder()
                .prisonFacilityName(stationModel.getPrisonFacilityName())
                .location(stationModel.getLocation())
                .latitude(stationModel.getLatitude())
                .longitude(stationModel.getLongitude())
                .prisonMaxStaffCapacity(stationModel.getPrisonMaxStaffCapacity())
                .currentStaffCapacity(stationModel.getCurrentStaffCapacity())
                .currentInmateCapacity(stationModel.getCurrentInmateCapacity())
                .inmateMaxCapacity(stationModel.getInmateMaxCapacity())
                .headOfPrison(foundUser)
                .build();


            listOfPrisonFacility.save(prisonFacility);
            var rank = prisonWardenRankRepository.findByRank("Controller of Corrections").orElseThrow(()-> new NotFoundException("prison rank not found..."));
            var dept = listOfPrisonDepartmentRepository.findByDepartment("Operations Department").orElseThrow();

            warden(rank.getRank(), prisonFacility.getPrisonFacilityName(), foundUser,dept.getId());
            appointHeadOfPrisonDepartment(userId, dept.getId());
            return Ok.builder()
                .message("prison facility creation successfully...")
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .date(LocalDateTime.now())
                .build();
        } catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    @Transactional
    public Ok<?> createWardenRank(WardenRanksEntity rank) {
        WardenRanksEntity wardenRanks = WardenRanksEntity.builder()
                .rank(rank.getRank())
                .build();
        prisonWardenRankRepository.save(wardenRanks);
        return Ok.builder()
                .date(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .statusName(HttpStatus.CREATED.name())
                .message("prison rank creation successful..")
                .build();
    }

    @Override
    @Transactional
    public Ok<?> createOccupation(String email, String rank,
                                  String station, Role occupation,
                                  Authentication connectedUser, Long deptId) throws NotFoundException {
        try {

            UserEntity savedUser = userServiceAuthentication.changeUserRole(email,
                    connectedUser,
                    occupation);

            if (Objects.requireNonNull(savedUser.getRole()) == Role.WARDEN) {
                warden(rank, station, savedUser, deptId);
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
    public Ok<?> finByPrisonRank(WardenRanksEntity rankId) {
        List<PoliceWardenJudgeEntity> res = prisonRepository.findAllByWardenRanks(rankId);
        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message(res)
                .build();
    }

    @Override
    public Ok<?> finByPrisonFacility(ListOfPrisonFacility facilityId) {
        List<PoliceWardenJudgeEntity> res = prisonRepository.findAllByListOFPrisonFacility(facilityId);

        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message(res)
                .build();
    }

    @Override
    @Transactional
    public Ok<?> createPrisonDepartment(ListOfDepartmentModel departmentEntity) {
        var department = ListOfPrisonDepartmentEntity.builder()
                .department(departmentEntity.getDepartment())
                .build();
        listOfPrisonDepartmentRepository.save(department);
        return Ok.builder()
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .date(LocalDateTime.now())
                .message("department created...")
                .build();

    }

    @Override
    @Transactional
    public Ok<?> updatePrisonDepartment(ListOfDepartmentModel departmentEntity,
                                        Long deptId) throws NotFoundException {
        var dept = listOfPrisonDepartmentRepository.findById(deptId)
                .orElseThrow(()-> new NotFoundException("department not found..."));

        if(Objects.nonNull(departmentEntity)){
            dept.setDepartment(departmentEntity.getDepartment());
        }

        listOfPrisonDepartmentRepository.save(dept);
        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message("update successful...")
                .build();
    }

    @Override
    @Transactional
    public Ok<?> appointHeadOfPrisonDepartment(Long userId, Long deptId) throws NotFoundException {
        var prisonDept = listOfPrisonDepartmentRepository.findById(deptId)
                .orElseThrow(()-> new NotFoundException("department not found..."));

        var prisonStaff = prisonRepository.findByUserEntity(userId)
                .orElseThrow(()-> new NotFoundException("invalid staff..."));
        if(!prisonStaff.getPrisonDepartmentId().getId().equals(deptId)) {
            prisonStaff.setPrisonDepartmentId(prisonDept);
            prisonRepository.save(prisonStaff);
        }
        if(!(prisonStaff.getUserEntity().getRole().equals(Role.WARDEN))) throw new IllegalArgumentException("this personnel is not a police officer");
        var head = HeadOfPrisonDepartmentEntity.builder()
                .department(prisonStaff.getPrisonDepartmentId())
                .prisonFacility(prisonStaff.getListOFPrisonFacility())
                .userId(prisonStaff.getUserEntity())
                .build();

        headOfPrisonDepartmentRepository.save(head);

        return Ok.builder()
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message("head of department saved...")
                .build();
    }


    private void warden(String rank, String station, UserEntity user, Long deptId) throws NotFoundException {
        var foundRank = prisonWardenRankRepository.findByRank(rank)
                .orElseThrow(() -> new NotFoundException("rank not found ....W"));
        var foundFacility = listOfPrisonFacility.findByPrisonFacilityName(station)
                .orElseThrow(() -> new NotFoundException("prison facility not found..."));
        ListOfPrisonDepartmentEntity department = null;
            department = listOfPrisonDepartmentRepository.findById(deptId)
                    .orElseThrow(() -> new NotFoundException("department not found..."));

        var currentCapacity = foundFacility.getCurrentStaffCapacity() + 1;
        if (currentCapacity > 100)
            throw new IllegalArgumentException("the station has reached it max staff capacity");
        foundFacility.setCurrentStaffCapacity(currentCapacity);

        var policeWardenJudge = PoliceWardenJudgeEntity.builder()
                .userEntity(user)
                .listOFPrisonFacility(foundFacility)
                .wardenRanks(foundRank)
                .prisonDepartmentId(department)
                .build();
        prisonRepository.save(policeWardenJudge);
    }
}
