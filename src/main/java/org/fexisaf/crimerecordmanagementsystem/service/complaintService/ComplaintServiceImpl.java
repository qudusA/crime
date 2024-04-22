package org.fexisaf.crimerecordmanagementsystem.service.complaintService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fexisaf.crimerecordmanagementsystem.dto.CrimeTypeCountDTO;
import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.fexisaf.crimerecordmanagementsystem.model.ComplaintModel;
import org.fexisaf.crimerecordmanagementsystem.repository.ComplaintRepository;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;


    @Override
    @Transactional
    public Ok<?> makeComplaint(ComplaintModel complaintModel) {

        ComplainEntity complaint = ComplainEntity.builder()
                .crimeType(complaintModel.getCrimeType())
                .location(complaintModel.getLocation())
                .description(complaintModel.getDescription())
                .build();


       complaintRepository.save(complaint);
        return Ok.builder()
                .date(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .statusName(HttpStatus.CREATED.name())
                .message("complaint submitted...")
                .build();
    }

    @Override
    public Ok<?> findHighestComplaint() {
        List<CrimeTypeCountDTO> complaint = complaintRepository.findHighestComplaint();

        return Ok.builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .date(LocalDateTime.now())
                .message(complaint)
                .build();
    }

    @Override
    public Ok<?> findAllComplaint() {
        List<ComplainEntity> complain = complaintRepository.findAll();
        return Ok.builder()
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .date(LocalDateTime.now())
                .message(complain)
                .build();
    }

    @Override
    public Ok<?> findComplaintByData(String data) {

        Timestamp timestampValue = null;
        try {

            timestampValue = Timestamp.valueOf(data);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid timestamp value: {}", data);
        }

        List<ComplainEntity> complaint = complaintRepository.findData(data, timestampValue);
        return Ok.builder()
                .date(LocalDateTime.now())
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message(complaint)
                .build();
    }

    @Override
    public List<ComplainEntity> findAllNonAddressedComplaint(LocalDateTime minus) {
        return complaintRepository.findAllByIsAddressedAndCreatedDate( false, minus);


    }

    @Override
    @Transactional
    public void save(ComplainEntity complaint) {
        complaintRepository.save(complaint);
    }
}
