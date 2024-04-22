package org.fexisaf.crimerecordmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"date_of_birth", "first_name","last_name"}))
public class SuspectEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suspectId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "suspect_case",
            uniqueConstraints =
            @UniqueConstraint(columnNames = {"caseEntities","suspectId"})
            ,joinColumns = @JoinColumn(name = "suspect_id"),
    inverseJoinColumns = @JoinColumn(name = "case_id")
    )
    private List<CaseEntity> caseEntities;

    @NotBlank(message = "first name is required")
    @NotNull(message = "first name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "last name is required")
    @NotNull(message = "last name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "date of birth is required")
    @NotNull(message = "date of birth is required")
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @NotBlank(message = "phone number is required")
    @NotNull(message = "phone number is required")
    @Pattern(regexp = "^\\+?\\d{8,15}$",message = "invalid phone number")
    @Column(name = "phone_number")
    private String phoneNumber;

    //    @NotNull(message = "email is required")
////    @NotBlank(message = "email cannot be blank")
//    @Column(name = "home_address")
    private String address;


}
