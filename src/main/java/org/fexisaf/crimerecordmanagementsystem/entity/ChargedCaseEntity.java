package org.fexisaf.crimerecordmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"case_id","court_house_id"}))
public class ChargedCaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargedCaseId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "case_id")
    private CaseEntity caseEntity;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "court_house_id")
    private ListOfCourtHouseEntity listOfCourtHouseEntity;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    @JoinColumn(name = "court_room_id")
//    private PoliceWardenJudgeEntity policeWardenJudge;


    @OneToMany(mappedBy = "chargedCaseId", orphanRemoval = true)
    private List<PunishmentEntity> punishmentEntities;

}
