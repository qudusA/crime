package org.fexisaf.crimerecordmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ListOfCourtHouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "listOfCourtHouses")
    private List<PoliceWardenJudgeEntity> policeWardenJudgeEntities;

    @Column(name = "court_house_name", unique = true)
    private String courtHouseName;

    @Column(name = "location")
    private String location;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @PositiveOrZero
    @Builder.Default
    private int numbersOfCourtRooms = 0;


}

