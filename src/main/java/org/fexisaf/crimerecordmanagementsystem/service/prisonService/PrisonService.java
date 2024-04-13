package org.fexisaf.crimerecordmanagementsystem.service.prisonService;

import org.fexisaf.crimerecordmanagementsystem.entity.ListOfPrisonFacility;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.WardenRanksEntity;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfDepartmentModel;
import org.fexisaf.crimerecordmanagementsystem.model.ListOfPrisonModel;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.security.core.Authentication;

public interface PrisonService {

    Ok<?> createPrisonFacility(ListOfPrisonModel stationModel, Long userId) throws Exception;

    Ok<?> createWardenRank(WardenRanksEntity rank);

    Ok<?> createOccupation(String email, String rank, String station,
                           Role occupation, Authentication connectedUser,Long deptId) throws NotFoundException;

    Ok<?> finByPrisonRank(WardenRanksEntity rankId);

    Ok<?> finByPrisonFacility(ListOfPrisonFacility facilityId);

    Ok<?> createPrisonDepartment(ListOfDepartmentModel departmentEntity);

    Ok<?> updatePrisonDepartment(ListOfDepartmentModel departmentEntity, Long deptId) throws NotFoundException;

    Ok<?> appointHeadOfPrisonDepartment(Long userId, Long deptId) throws NotFoundException;
}
