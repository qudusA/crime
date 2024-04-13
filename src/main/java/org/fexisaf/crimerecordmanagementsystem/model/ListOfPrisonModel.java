package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ListOfPrisonModel {



    @NotNull(message = "name is required..")
    private String prisonFacilityName;


    @NotNull(message = "location is required..")
    private String location;


    @NotNull(message = "coordinate is required..")
    @NotBlank(message = "cannot be blank")
    private String longitude;


    @NotNull(message = "coordinate is required..")
    @NotBlank(message = "cannot be blank")
    private String latitude;

    @NotNull(message = "capacity is required..")
    @Positive
    private int prisonMaxStaffCapacity;

    @NotNull(message = "capacity is required..")
    @PositiveOrZero
    private int currentStaffCapacity;

    @Positive
    private int inmateMaxCapacity;

    @PositiveOrZero
    private int currentInmateCapacity;

//    @Column(name = "head_of_police_station")
//    private S headOfPoliceStation;


}
