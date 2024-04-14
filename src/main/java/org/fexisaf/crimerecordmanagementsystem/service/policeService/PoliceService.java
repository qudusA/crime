package org.fexisaf.crimerecordmanagementsystem.service.policeService;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPoliceStationEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.PoliceRanksEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.SuspectEntity;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfDepartmentModel;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfPoliceStationModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.security.core.Authentication;

public interface PoliceService {

    Ok<?> createPoliceStation(ListOfPoliceStationModel policeStationModel,Long id) throws Exception;

    Ok<?> createPoliceRank(PoliceRanksEntity rank) throws Exception;

    Ok<?> createOccupation(String email, String rank,
                           String station, Role occupation,
                           Authentication connectedUser,
                           Long deptId
                           ) throws NotFoundException, NotFoundException;

    Ok<?> finByRank(PoliceRanksEntity id);

    Ok<?> finByStation(ListOfPoliceStationEntity id);

    Ok<?> createPoliceDepartment(ListOfDepartmentModel departmentEntity) throws Exception;

    Ok<?> updateDepartment(ListOfDepartmentModel departmentEntity, Long deptId) throws Exception;

    Ok<?> appointHeadOfDepartment(Long userId, Long deptId) throws Exception;


    Ok<?> createSuspect(Long caseId, SuspectEntity suspectEntity) throws Exception;
}
