package org.fexisaf.crimerecordmanagementsystem.service.caseService;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.CaseEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.fexisaf.crimerecordmanagementsystem.repository.CaseRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService{

  private final CaseRepository caseRepository;


    @Override
    public void saveComplaintToCase(ComplainEntity complaint) {

        CaseEntity caseEntity = CaseEntity.builder()
                .complain(complaint)
                .typeOfCase(complaint.getCrimeType())
                .build();
        caseRepository.save(caseEntity);
    }
}
