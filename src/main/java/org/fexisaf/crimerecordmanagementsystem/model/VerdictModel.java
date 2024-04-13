package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerdictModel {

    @NotBlank(message = "this field cannot be empty...")
    @NotNull(message = "this field cannot be Null...")
    private String verdict;

}
