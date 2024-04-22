package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintModel {


    @NotBlank(message = "location of the incident is required")
    @NotNull(message = "location of the incident is required")
    private String location;


    @NotNull(message = "crime type is required to dispatch the appropriate authority")
    @NotBlank(message = "crime type is required to dispatch the appropriate authority")
    private String crimeType;


    private String description;
}
