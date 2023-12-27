package com.unidac.breakfast.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unidac.breakfast.entity.BreakfastDay;
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
public class BreakfastDayDTO {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<UserDTO> collaborators = new ArrayList<>();
    private List<BreakfastItemDTO> items = new ArrayList<>();

    public BreakfastDayDTO(BreakfastDay entity) {
        this.id = entity.getId();
        this.date = entity.getDate();
        this.collaborators.addAll(entity.getCollaborators().stream().map(UserDTO::new).toList());
        this.items.addAll(entity.getItems().stream().map(BreakfastItemDTO::new).toList());
    }
}
