package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListOfPoliceDepartmentEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "department", orphanRemoval = true)
    private List<HeadOfDepartmentEntity> headOfDepartment;

    @OneToMany(mappedBy = "departmentId", orphanRemoval = true)
    private List<PoliceWardenJudgeEntity> policeWardenJudge;

}
