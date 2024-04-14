package org.fexisaf.crimerecordmanagementsystem.service.chargedCaseService;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.ChargedCaseEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.PoliceWardenJudgeEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.fexisaf.crimerecordmanagementsystem.event.chargedCaseEvent.ChargedCaseEvent;
import org.fexisaf.crimerecordmanagementsystem.repository.*;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChargedCaseServiceImpl implements ChargedCaseService{
   private final CaseRepository caseRepository;
   private final UserRepository userRepository;
   private final ListOfCourtHouseRepository listOfCourtHouseRepository;
   private final ChargedCaseRepository chargedCaseRepository;
   private final CourtRepository courtRepository;
   private final PunishmentRepository punishmentRepository;

   private final ApplicationEventPublisher publisher;



    @Override
    public Ok<?> findAllJudge() {

      List<UserEntity> user = userRepository.findByRole(Role.JUDGE);

        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message(user)
                .build();
    }



//    @Override
//    public void assignJudgeToCase(ChargedCaseEntity event) {
//       var courtId = event.getListOfCourtHouseEntity();
//       var foundCourtHouse = listOfCourtHouseRepository.findByCourtHouseName(courtId.getCourtHouseName()).orElseThrow();
//        Random random = new Random();
//        int lowerBound = 1;
//        int Bound = foundCourtHouse.getNumbersOfCourtRooms() - lowerBound;
//       var countIndex = lowerBound + random.nextInt(Bound);
//       List<PoliceWardenJudgeEntity> policeWardenJudgeEntities  =
//              courtRepository.findAllByListOfCourtHouses(courtId);
//       var foundCourtRoom = policeWardenJudgeEntities.get(countIndex);
//      event.setPoliceWardenJudge(foundCourtRoom);
//      chargedCaseRepository.save(event);
//    }


    @Override
    public Ok<?> createChargedCase(Long caseId, String courtHouse) throws NotFoundException {

        var foundCase = caseRepository.findById(caseId)
                .orElseThrow(()-> new NotFoundException("case not found..."));

        var foundCourtHouse = listOfCourtHouseRepository.findByCourtHouseName(courtHouse)
                .orElseThrow(()-> new NotFoundException("court house not found..."));

        ChargedCaseEntity caseEntity = ChargedCaseEntity.builder()
                .caseEntity(foundCase)
                .listOfCourtHouseEntity(foundCourtHouse)
                .build();

        ChargedCaseEntity chargedCaseEntity = chargedCaseRepository.save(caseEntity);

        publisher.publishEvent(new ChargedCaseEvent(chargedCaseEntity));

        return Ok.builder()
                .date(LocalDateTime.now())
                .statusName(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .message(caseEntity)
                .build();
    }




}
