package com.unidac.breakfast.dtos.response;

import com.unidac.breakfast.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String cpf;
    private List<BreakfastItemDTO> items = new ArrayList<>();

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.items.addAll(entity.getItems().stream().map(BreakfastItemDTO::new).toList());
    }

}
