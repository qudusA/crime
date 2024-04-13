package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Data
public class OpenCaseOnMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "to_match")
    @NotBlank(message = "toMatch cannot be blank")
    @NotEmpty(message = "toMatch cannot be Empty")
    private String toMatch;
    public OpenCaseOnMatch(String toMatch){
    this.toMatch = toMatch;
    }

}
