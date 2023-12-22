package com.unidac.breakfast.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemInsertDTO {
    private String name;
    private Boolean missing;
    private Long collaboratorId;
}
