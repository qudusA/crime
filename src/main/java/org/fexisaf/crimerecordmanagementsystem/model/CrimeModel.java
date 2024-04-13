package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CrimeModel {

    @NotNull(message = "you have to describe the incident")
    @NotBlank(message = "description cannot be blank")
    private String description;

//    @Builder.Default
//    private boolean anonymous = false;

    @NotNull(message = "crime location is required to send appropriate authority...")
    @NotBlank(message = "location cannot be blank")
    private String location;

    @Column(name = "crime_type")
    private String crimeType;

    @Column(name = "status")
    private String status;

    @Column(nullable = false)
    private String date;

}
