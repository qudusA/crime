package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListOfCourtRoomsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "room_number", unique = true)
    private String roomNumber;


    @OneToMany(mappedBy = "courtRoomId", orphanRemoval = true)
    private List<PoliceWardenJudgeEntity> policeWardenJudge;

    @OneToMany(mappedBy = "RoomId", orphanRemoval = true)
    private List<CaseAssignedToJudge> caseAssignedToJudges;


}
