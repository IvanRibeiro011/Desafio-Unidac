package com.unidac.breakfast.dtos.response;

import com.unidac.breakfast.entity.BreakfastItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BreakfastItemDTO {
    private Long id;
    private String name;
    private Boolean missing;
    private Long collaboratorId;
    private Long breakfastId;

    public BreakfastItemDTO(BreakfastItem entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.missing = entity.getMissing();
        this.collaboratorId = entity.getCollaborator().getId();
        this.breakfastId = entity.getBreakfast().getId();
    }
}
