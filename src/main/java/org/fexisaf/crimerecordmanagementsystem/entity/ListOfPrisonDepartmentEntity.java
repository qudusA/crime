package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListOfPrisonDepartmentEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department", unique = true)
    @NotNull(message = "department field cannot be null")
    @NotBlank(message = "department field cannot be blank")
    private String department;

    @OneToMany(mappedBy = "department", orphanRemoval = true)
    private List<HeadOfPrisonDepartmentEntity> headOfPrisonDepartment;

    @OneToMany(mappedBy = "prisonDepartmentId", orphanRemoval = true)
    private List<PoliceWardenJudgeEntity> policeWardenJudge;

}
