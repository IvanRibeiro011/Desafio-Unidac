package com.unidac.breakfast.dtos.response;

import com.unidac.breakfast.entity.BreakfastDay;
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

    private List<BreakfastDayDTO> days = new ArrayList<>();

    public UserDTO(Long id, String name, String cpf) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
    }

    public UserDTO(User entity , List<BreakfastItemDTO> i) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.items.addAll(i);
    }

//    public UserDTO(User entity,List<BreakfastDayDTO> breakfastDay) {
//        this.id = entity.getId();
//        this.name = entity.getName();
//        this.cpf = entity.getCpf();
//        this.days.addAll(breakfastDay);
//    }

}
