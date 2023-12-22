package com.unidac.breakfast.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO {
    @NotBlank(message = "Campo requerido")
    private String name;
    @CPF(message = "O campo precisa ter um formato de CPF válido")
    private String cpf;
}
