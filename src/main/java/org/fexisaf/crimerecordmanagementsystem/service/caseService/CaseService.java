package org.fexisaf.crimerecordmanagementsystem.service.caseService;

import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;

public interface CaseService {
    void saveComplaintToCase(ComplainEntity complaint);

    Ok<?> saveComplaintToCaseByUser(Long complaintId) throws NotFoundException;
}
