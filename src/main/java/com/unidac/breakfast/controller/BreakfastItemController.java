package com.unidac.breakfast.controller;

import com.unidac.breakfast.dtos.request.ItemInsertDTO;
import com.unidac.breakfast.dtos.response.BreakfastItemDTO;
import com.unidac.breakfast.service.BreakfastItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Items", description = "Operações relacionadas a itens")
@RestController
@RequestMapping("/items")
public class BreakfastItemController {

    private final BreakfastItemService service;

    public BreakfastItemController(BreakfastItemService service) {
        this.service = service;
    }

    @Operation(summary = "Busca um registro de item de café da manhã")
    @GetMapping("{id}")
    public ResponseEntity<BreakfastItemDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Busca todos os  registros de itens de café da manhã")
    @GetMapping
    public ResponseEntity<List<BreakfastItemDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Atualiza um registro de item de café da manhã")
    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody BreakfastItemDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Item alterado com sucesso");
    }

    @Operation(summary = "Exclui um registro de item de café da manhã")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Item excluido com sucesso");
    }
}
