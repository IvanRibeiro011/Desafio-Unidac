package com.unidac.breakfast.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemInsertDTO {
    @NotBlank(message = "Campo requerido")
    private String name;
    private Boolean missing;
    @NotNull(message = "Campo requerido")
    private Long collaboratorId;
}
