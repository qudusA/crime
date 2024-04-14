package org.fexisaf.crimerecordmanagementsystem.service.caseService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.CaseEntity;
import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.fexisaf.crimerecordmanagementsystem.repository.CaseRepository;
import org.fexisaf.crimerecordmanagementsystem.repository.ComplaintRepository;
import org.fexisaf.crimerecordmanagementsystem.response.error.NotFoundException;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional annotation

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;
    private final ComplaintRepository complaintRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveComplaintToCase(ComplainEntity complaint) {
        ComplainEntity managedComplaint = entityManager.merge(complaint);

        CaseEntity caseEntity = CaseEntity.builder()
                .complain(managedComplaint)
                .typeOfCase(managedComplaint.getCrimeType())
                .build();

        caseRepository.save(caseEntity);
    }

    @Override
    public Ok<?> saveComplaintToCaseByUser(Long complaintId) throws NotFoundException {

       ComplainEntity complain = complaintRepository.findById(complaintId)
                .orElseThrow(()-> new NotFoundException("complaint not found..."));

        CaseEntity caseEntity = CaseEntity.builder()
                .complain(complain)
                .typeOfCase(complain.getCrimeType())
                .build();

        caseRepository.save(caseEntity);
        complain.setAddressed(true);
        complaintRepository.save(complain);


        return Ok.builder()
                .message("case opened...")
                .statusCode(HttpStatus.CREATED.value())
                .statusName(HttpStatus.CREATED.name())
                .date(LocalDateTime.now())
                .build();
    }
}
