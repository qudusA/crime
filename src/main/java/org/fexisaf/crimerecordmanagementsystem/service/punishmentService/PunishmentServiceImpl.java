package org.fexisaf.crimerecordmanagementsystem.service.punishmentService;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.*;
import org.fexisaf.crimerecordmanagementsystem.model.PunishmentModel;
import org.fexisaf.crimerecordmanagementsystem.repository.AssignCaseToCourtRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.CaseRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.CourtRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.PunishmentRepository;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PunishmentServiceImpl implements PunishmentService{

    private final PunishmentRepository punishmentRepository;
    private final AssignCaseToCourtRepository assignCaseToCourtRepository;
    private final CourtRepository courtRepository;

    @Override
    @Transactional
    public Ok<?> savePunishment(PunishmentModel punishmentModel,
                                Long chargedCaseId) throws Exception {
       try {
           CaseAssignedToJudge chargedCase = assignCaseToCourtRepository.findById(chargedCaseId)
                   .orElseThrow(() -> new NotFoundException("criminal not found..."));

           UserEntity loggedInUser = (UserEntity) SecurityContextHolder
                   .getContext().getAuthentication().getPrincipal();

           PoliceWardenJudgeEntity policeWardenJudge = courtRepository.findByUserEntity(loggedInUser.getId())
                   .orElseThrow(() -> new NotFoundException("user not found..."));

           if(!policeWardenJudge.getCourtRoomId().equals(chargedCase.getRoomId()))
               throw new IllegalArgumentException("you are not the assigned judge");

           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
           var startDate = LocalDate.parse(punishmentModel.getStartDate(), formatter);
           var endDate = LocalDate.parse(punishmentModel.getEndDate(), formatter);
           if(startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())){
               throw new IllegalArgumentException("kind check the input date...");
           }

           PunishmentEntity punishmentEntity;
           switch(punishmentModel.getPunishmentType().toLowerCase()){
                case "fine" ->{
                    if(punishmentModel.getFineAmount() == null){
                        throw new IllegalArgumentException("fined amount is required cr...");
                    }
                     punishmentEntity = PunishmentEntity.builder()
                            .chargedCaseId(chargedCase.getCaseEntity())
                            .punishmentType("fine")
                            .punishmentDescription(punishmentModel.getPunishmentDescription())
                            .amountPaid(0)
                            .fineAmount(punishmentModel.getFineAmount())
                            .startDate(startDate)
                            .endDate(endDate)
                            .build();
                }
                case "imprisonment" -> {
                     punishmentEntity = PunishmentEntity.builder()
                            .chargedCaseId(chargedCase.getCaseEntity())
                            .punishmentType("imprisonment")
                            .punishmentDescription(punishmentModel.getPunishmentDescription())
                            .startDate(startDate)
                            .endDate(endDate)
                            .build();
                }
                case "community service" -> {
                    punishmentEntity = PunishmentEntity.builder()
                            .chargedCaseId(chargedCase.getCaseEntity())
                            .punishmentType("community service")
                            .punishmentDescription(punishmentModel.getPunishmentDescription())
                            .startDate(startDate)
                            .endDate(endDate)
                            .build();
                }
                default ->throw  new IllegalArgumentException("kindly verify your input and try again..");
           }

           punishmentRepository.save(punishmentEntity);
           chargedCase.getCaseEntity().getCaseEntity().setCaseStatus("closed");
           assignCaseToCourtRepository.save(chargedCase);
           return Ok.builder()
                   .date(LocalDateTime.now())
                   .statusCode(HttpStatus.CREATED.value())
                   .statusName(HttpStatus.CREATED.name())
                   .message("punishment successfully saved...")
                   .build();
       }catch (NotFoundException e){
           throw new NotFoundException(e.getMessage());
       }catch (Exception e){
           throw new Exception(e.getMessage());
       }
    }

    @Override
    public Ok<?> findByPunishmentType(String type) throws NotFoundException {

        List<PunishmentEntity> res = punishmentRepository.findAllByPunishmentType(type);

        return Ok.builder()
                .date(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .statusName(HttpStatus.CREATED.name())
                .message(res)
                .build();
    }



    @Override
    @Transactional
    public Ok<?> deleteById(Long punishmentId) throws Exception {
        try {

            UserEntity loggedInUser = (UserEntity) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
           var punishment = punishmentRepository.findById(punishmentId)
                   .orElseThrow(()-> new NotFoundException("punishment not found..."));
           if(!punishment.getCreatedBy().equals(loggedInUser.getId()))
               throw new IllegalArgumentException("you did not give the punishment");

            punishmentRepository.deleteById(punishmentId);

            return Ok.builder()
                    .date(LocalDateTime.now())
                    .statusCode(HttpStatus.CREATED.value())
                    .statusName(HttpStatus.CREATED.name())
                    .message("punishment deleted...")
                    .build();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Ok<?> updateById(PunishmentModel punishmentModel, Long punishmentId) throws Exception {
        if(punishmentModel == null)throw new IllegalArgumentException("punishment field cannot be empty...");
        PunishmentEntity foundPunishment = punishmentRepository.findById(punishmentId)
                .orElseThrow(()-> new NotFoundException("punishment not found..."));

        UserEntity loggedInUser = (UserEntity) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        if(!foundPunishment.getCreatedBy().equals(loggedInUser.getId()))
            throw new IllegalArgumentException("you did not give the punishment");

        if( punishmentModel.getPunishmentDescription() != null && !punishmentModel.getPunishmentDescription().isBlank()){

            foundPunishment.setPunishmentDescription(punishmentModel.getPunishmentDescription());
        }

        if( punishmentModel.getPunishmentType() != null && !punishmentModel.getPunishmentType().isBlank() ){

            foundPunishment.setPunishmentType(punishmentModel.getPunishmentType());
            foundPunishment.setFineAmount(0);

        }

        if(punishmentModel.getAmountPaid() != null ){

            foundPunishment.setPunishmentDescription(punishmentModel.getPunishmentDescription());
        }

        if(punishmentModel.getFineAmount() != null ){

            foundPunishment.setFineAmount(punishmentModel.getFineAmount());
        }

try {

    punishmentRepository.save(foundPunishment);

    return Ok.builder().statusCode(HttpStatus.OK.value())
            .statusName(HttpStatus.OK.name())
            .statusCode(HttpStatus.OK.value())
            .date(LocalDateTime.now())
            .message("punishment update successful...")
            .build();
}catch (Exception e){
    throw new Exception(e.getMessage());
}
    }


}
