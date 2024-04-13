package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CriminalModel {

    @Column(name = "criminal_email", unique = true)
    private String email;

    @Column(name = "criminal_first_name")
    private String firstName;

    @Column(name = "criminal_last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

//    @Column(name = "photograph")
//    private String photograph;

    @Column(name = "date_of_birth" )
    private String dateOfBirth;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

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
