package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListOfDepartmentModel {


    @NotNull(message = "department Name cannot be null...")
    @NotBlank(message = "department name can not be empty...")
    private String department;

}
