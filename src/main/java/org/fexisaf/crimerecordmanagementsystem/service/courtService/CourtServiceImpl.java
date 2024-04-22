package org.fexisaf.crimerecordmanagementsystem.service.courtService;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.*;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfCourtHouseModel;
import org.fexisaf.crimerecordmanagementsystem.repository.*;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.userService.UserServiceAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService{
    private final ListOfCourtHouseRepository listOfCourtHouseRepository;
    private final ListOfCourtRoomsRepository listOfCourtRoomsRepository;
    private final UserServiceAuthentication userServiceAuthentication;
    private final CourtRepository courtRepository;
    private final ChargedCaseRepository chargedCaseRepository;
    private final AssignCaseToCourtRepository assignCaseToCourtRepository;


    @Override
    @Transactional
    public Ok<?> createCourtHouse(ListOfCourtHouseModel courtHouseModel) {

        var courtHouse = ListOfCourtHouseEntity.builder()
                .courtHouseName(courtHouseModel.getCourtHouseName())
                .longitude(courtHouseModel.getLongitude())
                .latitude(courtHouseModel.getLatitude())
                .location(courtHouseModel.getLocation())
                .build();

        listOfCourtHouseRepository.save(courtHouse);

        return Ok.builder()
                .message("court house created...")
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .date(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public Ok<?> createCourtRoom(ListOfCourtRoomsEntity listOfCourtRooms, Long courtHouseId) throws NotFoundException {

        var courtHouse = listOfCourtHouseRepository.findById(courtHouseId).orElseThrow();

        var courtRooms = ListOfCourtRoomsEntity.builder()
               .roomNumber(listOfCourtRooms.getRoomNumber())
               .listOfCourtHouseEntity(courtHouse)
               .build();

        listOfCourtRoomsRepository.save(courtRooms);
        judge(courtHouse);
        return Ok.builder()
                .message("court house created...")
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .date(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public Ok<?> createOccupation(String email,
                                  Role occupation, Authentication connectedUser,
                                  Long roomId) throws NotFoundException {

        try {

            UserEntity savedUser = userServiceAuthentication.changeUserRole(email, connectedUser,
                    occupation);

            if (Objects.requireNonNull(savedUser.getRole()) == Role.JUDGE) {
                var foundRoom = listOfCourtRoomsRepository.findByRoomId(roomId)
                        .orElseThrow(() -> new NotFoundException("court room not found"));
//
                var policeWardenJudge = PoliceWardenJudgeEntity.builder()
                        .userEntity(savedUser)
                        .courtRoomId(foundRoom)
                        .listOfCourtHouses(foundRoom.getListOfCourtHouseEntity())
                        .build();
                courtRepository.save(policeWardenJudge);

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
    public Ok<?> findAllCourtHouse() {

      List<ListOfCourtHouseEntity> courtHouse = listOfCourtHouseRepository.findAll();


        return Ok.builder()
                .message(courtHouse)
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .date(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public Ok<?> assignCaseToJudge(Long roomId, Long caseId) throws NotFoundException {

        UserEntity user =(UserEntity) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
//        if(user.getRole() != Role.JUDGE)
//            throw new IllegalArgumentException("you cant perform this action...");
       var courtRoom = listOfCourtRoomsRepository.findById(roomId)
                .orElseThrow(()-> new NotFoundException("room not found..."));

        var chargedCase = chargedCaseRepository.findById(caseId)
                .orElseThrow(()-> new NotFoundException("case not found..."));
    if(!courtRoom.getListOfCourtHouseEntity().getId()
            .equals(chargedCase.getListOfCourtHouseEntity().getId()))throw new IllegalArgumentException("assigned to wrong court");

    var caseAssigned = CaseAssignedToJudge.builder()
                .caseEntity(chargedCase)
                .RoomId(courtRoom)
                .build();
        assignCaseToCourtRepository.save(caseAssigned);

        return Ok.builder()
                .date(LocalDateTime.now())
                .message("case assigned to room "+courtRoom.getRoomNumber())
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }


    private void judge(ListOfCourtHouseEntity courtHouse) throws NotFoundException {

        var incrementResult = courtHouse.getNumbersOfCourtRooms() + 1;

        courtHouse.setNumbersOfCourtRooms(incrementResult);
        listOfCourtHouseRepository.save(courtHouse);

    }


}
