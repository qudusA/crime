package org.fexisaf.crimerecordmanagementsystem.response.ok;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
public class Ok<T> {

    private T message;


    private String statusName;


    private int statusCode;


    private LocalDateTime date;
}
