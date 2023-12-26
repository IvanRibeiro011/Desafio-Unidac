package com.unidac.breakfast.dtos.request;

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
public class BreakfastAssociationDTO {

    private LocalDate date;
    @NotNull(message = "Campo requerido")
    private Long itemId;
    @NotNull(message = "Campo requerido")
    private Long collaboratorId;
}
