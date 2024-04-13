package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListOfCourtHouseModel {


    @NotNull(message = "you need to provide court name...")
    @NotBlank(message = "you need to provide court name...")
    private String courtHouseName;

    @NotNull(message = "you need to provide court location...")
    @NotBlank(message = "you need to provide court location...")
    private String location;

    @NotNull(message = "you need to provide coordinate...")
    @NotBlank(message = "you need to provide coordinate...")
    private String longitude;

    @NotNull(message = "you need to provide coordinate..")
    @NotBlank(message = "you need to provide coordinate...")
    private String latitude;

}
