package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ListOfPoliceStationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "police_station_name", unique = true)
    private String policeStationName;

    @Column(name = "location")
    private String location;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "police_station_max_staff_capacity")
    @NotNull(message = "capacity is required..")
    @Positive
    private int policeStationMaxStaffCapacity;

    @Column(name = "police_station_current_staff_capacity")
    @PositiveOrZero
//    @Builder.Default
    private int currentCapacity;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "head_id")
    private UserEntity headOfPoliceStation;

    @OneToMany(mappedBy = "policeStation", orphanRemoval = true)
    private List<HeadOfDepartmentEntity> headOfDepartment;

    @OneToMany(mappedBy = "listOfPoliceStation", orphanRemoval = true)
    private List<PoliceWardenJudgeEntity> policeWardenJudge;
}

