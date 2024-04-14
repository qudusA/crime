package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseAssignedToJudge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "room_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ListOfCourtRoomsEntity RoomId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "case_id")
    private ChargedCaseEntity caseEntity;

}

