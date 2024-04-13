package org.fexisaf.crimerecordmanagementsystem.service.courtService;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.*;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfCourtHouseModel;
import org.fexisaf.crimerecordmanagementsystem.repository.CourtRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.ListOfCourtHouseRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.ListOfCourtRoomsRepository;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.fexisaf.crimerecordmanagementsystem.service.userService.UserServiceAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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



    @Override
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
    public Ok<?> createCourtRoom(ListOfCourtRoomsEntity listOfCourtRooms) {
       var courtRooms = ListOfCourtRoomsEntity.builder()
               .roomNumber(listOfCourtRooms.getRoomNumber())
               .build();

       listOfCourtRoomsRepository.save(courtRooms);
        return Ok.builder()
                .message("court house created...")
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .date(LocalDateTime.now())
                .build();
    }

    @Override
    public Ok<?> createOccupation(String email, String courtHouse,
                                  Role occupation, Authentication connectedUser,
                                  Long roomId) throws NotFoundException {

        try {

            UserEntity savedUser = userServiceAuthentication.changeUserRole(email, connectedUser,
                    occupation);

            if (Objects.requireNonNull(savedUser.getRole()) == Role.JUDGE) {
                judge( courtHouse, savedUser, roomId);

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


    private void judge(String courtHouse, UserEntity user, Long roomId) throws NotFoundException {
        var foundRoom = listOfCourtRoomsRepository.findByRoomId(roomId)
                .orElseThrow(() -> new NotFoundException("court room not found"));
        var foundCourtHouse = listOfCourtHouseRepository.findByCourtHouseName(courtHouse)
                .orElseThrow(() -> new NotFoundException("court house station not found..."));
        var incrementResult = foundCourtHouse.getNumbersOfCourtRooms() + 1;
        var policeWardenJudge = PoliceWardenJudgeEntity.builder()
                .userEntity(user)
                .courtRoomId(foundRoom)
                .listOfCourtHouses(foundCourtHouse)
                .build();

        foundCourtHouse.setNumbersOfCourtRooms(incrementResult);
        listOfCourtHouseRepository.save(foundCourtHouse);
        courtRepository.save(policeWardenJudge);
    }


}
