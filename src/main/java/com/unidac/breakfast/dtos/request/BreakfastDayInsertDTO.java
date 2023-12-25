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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BreakfastDayInsertDTO {
    @FutureOrPresent(message = "A data passada deve ser maior que a data atual")
    private LocalDate date;
    @NotEmpty(message = "Favor atribuir os items ao café da manhã")
    private List<ItemInsertDTO> items = new ArrayList<>();
}
