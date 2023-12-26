package com.unidac.breakfast.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApiArgumentNotValidMessage extends ApiErrorMessage {

    private List<ApiFieldError> errors = new ArrayList<>();

    public void addError(ApiFieldError error) {
        this.errors.add(error);
    }
}
