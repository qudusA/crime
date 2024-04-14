package org.fexisaf.crimerecordmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"courtRoomId", "listOfCourtHouses"}))
public class PoliceWardenJudgeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "police_rank_id")
    private PoliceRanksEntity policeRanks;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "warden_rank_id")
    private WardenRanksEntity wardenRanks;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    @JsonIgnore
    private ListOfPoliceDepartmentEntity departmentId;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "prison_department_id")
    @ToString.Exclude
    @JsonIgnore
    private ListOfPrisonDepartmentEntity prisonDepartmentId;


    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "court_room_id")
    private ListOfCourtRoomsEntity courtRoomId;


    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "police_station_id")
    private ListOfPoliceStationEntity listOfPoliceStation;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_id")
    private ListOfPrisonFacility listOFPrisonFacility;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "court_house_id")
    private ListOfCourtHouseEntity listOfCourtHouses;





}
