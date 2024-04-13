package org.fexisaf.crimerecordmanagementsystem.dto;


import java.time.LocalDate;

public class CrimeDTO {
    private Long id;
    private String crimeType;
    private String crimeDescription;
    private LocalDate crimeDate;

    public CrimeDTO(Long id, String crimeType, String crimeDescription, LocalDate crimeDate) {
        this.id = id;
        this.crimeType = crimeType;
        this.crimeDescription = crimeDescription;
        this.crimeDate = crimeDate;
    }

    // Add getters and setters as needed
}
