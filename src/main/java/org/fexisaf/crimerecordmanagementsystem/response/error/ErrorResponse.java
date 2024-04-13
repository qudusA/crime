package org.fexisaf.crimerecordmanagementsystem.response.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
//@Builder
public class ErrorResponse {

    private String message;


    private String statusName;


    private int statusCode;


    private LocalDateTime date;

}
