package org.fexisaf.crimerecordmanagementsystem.util;

import lombok.RequiredArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.ComplainEntity;
import org.fexisaf.crimerecordmanagementsystem.service.caseService.CaseService;
import org.fexisaf.crimerecordmanagementsystem.service.complaintService.ComplaintService;
import org.fexisaf.crimerecordmanagementsystem.service.openCaseOnMatctService.OpenCaseOnMatchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerComponent {

    private final ComplaintService complaintService;
    private final OpenCaseOnMatchService openCaseOnMatchService;
    private final CaseService caseService;

    @Scheduled(fixedDelay = 60_000, initialDelay = 7000)
    public void search(){

      List<ComplainEntity> complaints =
              complaintService.findAllNonAddressedComplaint(LocalDateTime
                      .now().minus(Duration.ofMinutes(5)));

      for(ComplainEntity complaint : complaints ){
          boolean onMatch = openCaseOnMatchService.findAllService(complaint);
          if(onMatch){
              caseService.saveComplaintToCase(complaint);
              complaint.setAddressed(true);
              complaintService.save(complaint);

          }
//          else {
//              System.out.println("clark");
//          }
      }


    }

}
