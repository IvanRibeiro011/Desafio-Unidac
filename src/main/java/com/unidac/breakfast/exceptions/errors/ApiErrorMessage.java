package com.unidac.breakfast.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiErrorMessage {

    private Integer status;
    private Instant timestamp;
    private String error;
    private String message;
    private String path;
}
