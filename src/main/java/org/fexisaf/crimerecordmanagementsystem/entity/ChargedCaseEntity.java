package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChargedCaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargedCaseId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "case_id")
    private CaseEntity caseEntity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "court_house_id")
    private ListOfCourtHouseEntity listOfCourtHouseEntity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "court_room_id")
    private PoliceWardenJudgeEntity policeWardenJudge;


    @OneToMany(mappedBy = "chargedCaseId")
    private List<PunishmentEntity> punishmentEntities;

}
