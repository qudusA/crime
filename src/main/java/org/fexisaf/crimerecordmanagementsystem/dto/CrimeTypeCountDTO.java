package org.fexisaf.crimerecordmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrimeTypeCountDTO {
    private String crimeType;
    private Long crimeCount;

    // Constructor, getters, and setters
}
