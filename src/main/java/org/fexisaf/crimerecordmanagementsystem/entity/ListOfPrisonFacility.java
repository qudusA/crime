package org.fexisaf.crimerecordmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListOfPrisonFacility {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "listOFPrisonFacility")
    private List<PoliceWardenJudgeEntity> policeWardenJudgeEntities;

//    @OneToMany(mappedBy = "prisonFacility")
//    private List<InmateEntity> inmateEntities;

    @Column(name = "prison_facility_name", unique = true)
    private String prisonFacilityName;

    @Column(name = "location")
    private String location;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @NotNull(message = "capacity is required..")
    @Column(name = "inmate_max_capacity")
    @Positive
    private int inmateMaxCapacity;

    @Column(name = "prison_current_inmate_capacity")
    @PositiveOrZero
    private int currentInmateCapacity;

    @Column(name = "prison_max_staff_capacity")
    @Positive
    private int prisonMaxStaffCapacity;

    @Column(name = "prison_current_staff_capacity")
    @PositiveOrZero
//    @Builder.Default
    private int currentStaffCapacity;



    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "head_id")
    private UserEntity headOfPrison;
}
