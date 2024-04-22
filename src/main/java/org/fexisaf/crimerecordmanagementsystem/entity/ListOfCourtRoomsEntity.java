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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"room_number", "court_house_id"}))
public class ListOfCourtRoomsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "room_number")
    private String roomNumber;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "court_House_id")
    private ListOfCourtHouseEntity listOfCourtHouseEntity;


    @OneToMany(mappedBy = "courtRoomId", orphanRemoval = true)
    private List<PoliceWardenJudgeEntity> policeWardenJudge;

    @OneToMany(mappedBy = "RoomId", orphanRemoval = true)
    private List<CaseAssignedToJudge> caseAssignedToJudges;


}
