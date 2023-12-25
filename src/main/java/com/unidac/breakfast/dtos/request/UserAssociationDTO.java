package com.unidac.breakfast.dtos.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAssociationDTO {
    @FutureOrPresent(message = "A data passada deve ser maior que a data atual")
    private LocalDate date;
    private Long collaboratorId;
    @NotEmpty(message = "Favor atribuir os items ao café da manhã")
    private List<ItemAssociationDTO> items = new ArrayList<>();
}
