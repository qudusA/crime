package org.fexisaf.crimerecordmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table( uniqueConstraints = @UniqueConstraint(columnNames =
        {"criminal_name","criminal_age", "criminal_gender","criminal_address"}))
public class CriminalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "criminal_id")
    private Long criminalId;


    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "crime_id")
    private CrimeEntity crimeEntities;

    @Column(name = "criminal_email", unique = true)
    private String email;

    @Column(name = "criminal_first_name")
    private String firstName;

    @Column(name = "criminal_last_name")
    private String lastName;

    @Column(name = "criminal_age")
    private Integer age;

    @Column(name = "criminal_gender")
    private String gender;

    @Column(name = "criminal_address")
    private String address;

    @Column(name = "criminal_photograph")
    private String photograph;

    @Column(name = "criminal_date_of_birth" )
    private String dateOfBirth;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private double weight;

    @Column(name = "eye_color")
    private String eyeColor;

    @Column(name = "hair_color")
    private String hairColor;

    @Column(name = "tattoos")
    private String tattoos;

    @Column(name = "scars")
    private String scars;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "contact_number")
    private String contactNumber;
}
