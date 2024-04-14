package org.fexisaf.crimerecordmanagementsystem.service.courtService;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfCourtRoomsEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfCourtHouseModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.security.core.Authentication;

public interface CourtService {
    Ok<?> createCourtHouse(ListOfCourtHouseModel courtHouseModel);

    Ok<?> createCourtRoom(ListOfCourtRoomsEntity listOfCourtRooms);

    Ok<?> createOccupation(String email, String courtHouse, Role occupation, Authentication connectedUser, Long roomId) throws NotFoundException;

    Ok<?> findAllCourtHouse();

    Ok<?> assignCaseToJudge(Long roomId, Long caseId) throws NotFoundException;
}
