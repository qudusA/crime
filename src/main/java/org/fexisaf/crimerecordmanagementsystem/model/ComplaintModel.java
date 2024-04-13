package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintModel {

    @Column(name = "location")
    @NotBlank(message = "location of the incident is required")
    @NotNull(message = "location of the incident is required")
    private String location;

    @Column(name = "crime_type")
    @NotNull(message = "crime type is required to dispatch the appropriate authority")
    @NotBlank(message = "crime type is required to dispatch the appropriate authority")
    private String crimeType;

    @Column(name = "description")
    private String description;
}
