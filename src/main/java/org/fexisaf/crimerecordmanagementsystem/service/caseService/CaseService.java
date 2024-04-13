package org.fexisaf.crimerecordmanagementsystem.service.caseService;

import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;

public interface CaseService {
    void saveComplaintToCase(ComplainEntity complaint);
}
