package org.fexisaf.crimerecordmanagementsystem.service.complaintService;

import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.fexisaf.crimerecordmanagementsystem.model.ComplaintModel;
import org.fexisaf.crimerecordmanagementsystem.response.ok.Ok;

import java.time.LocalDateTime;
import java.util.List;

public interface ComplaintService {

    Ok<?> makeComplaint(ComplaintModel complaintModel);

    Ok<?> findHighestComplaint();

    Ok<?> findAllComplaint();

    Ok<?> findComplaintByData(String data);

    List<ComplainEntity> findAllNonAddressedComplaint(LocalDateTime minus);

    void save(ComplainEntity complaint);
}
