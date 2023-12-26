package com.unidac.breakfast.controller;

import com.unidac.breakfast.dtos.request.BreakfastAssociationDTO;
import com.unidac.breakfast.dtos.request.BreakfastDayInsertDTO;
import com.unidac.breakfast.dtos.request.UserAssociationDTO;
import com.unidac.breakfast.dtos.response.BreakfastDayDTO;
import com.unidac.breakfast.service.BreakfastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Café da manhã", description = "Operações relacionadas ao café da manhã")
@RestController
@RequestMapping("/breakfast")
public class BreakfastController {

    private final BreakfastService service;

    public BreakfastController(BreakfastService service) {
        this.service = service;
    }

    @Operation(summary = "Busca um café da manhã por id")
    @GetMapping("{id}")
    public ResponseEntity<BreakfastDayDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Busca todos os registros de café da manhã")
    @GetMapping
    public ResponseEntity<List<BreakfastDayDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Insere um registro de café da manhã no banco de dados")
    @PostMapping
    public ResponseEntity<String> insert(@Valid @RequestBody BreakfastDayInsertDTO dto) {
        service.insert(dto);
        return ResponseEntity.ok("Café da manhã criado com sucesso");
    }

    @Operation(summary = "Associa um registro de item a um café da manhã no banco de dados")
    @PutMapping("/associate-item")
    public ResponseEntity<String> associateItem(@Valid @RequestBody BreakfastAssociationDTO dto) {
        service.associateItem(dto);
        return ResponseEntity.ok("Item associado com sucesso");
    }

    @Operation(summary = "Atualiza um registro de café da manhã ")
    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody BreakfastDayInsertDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Café da manhã alterado com sucesso");
    }

    @Operation(summary = "Associa um usuário e itens a um registro de café da manhã")
    @PutMapping("/associate-user")
    public ResponseEntity<String> associateToBreakfast(@Valid @RequestBody UserAssociationDTO dto) {
        service.associateUserToBreakfast(dto);
        return ResponseEntity.ok("Associação concluída com sucesso");
    }

    @Operation(summary = "Exclui um registro de café da manhã e seus itens associados")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Café da manhã excluido com sucesso");
    }
}
