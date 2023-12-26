package com.unidac.breakfast.controller;

import com.unidac.breakfast.dtos.request.BreakfastDayInsertDTO;
import com.unidac.breakfast.dtos.request.UserAssociationDTO;
import com.unidac.breakfast.dtos.response.BreakfastDayDTO;
import com.unidac.breakfast.service.BreakfastService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breakfast")
public class BreakfastController {

    private final BreakfastService service;

    public BreakfastController(BreakfastService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<BreakfastDayDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<BreakfastDayDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<String> insert(@Valid @RequestBody BreakfastDayInsertDTO dto) {
        service.insert(dto);
        return ResponseEntity.ok("Café da manhã criado com sucesso");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody BreakfastDayInsertDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Café da manhã alterado com sucesso");
    }

    @PutMapping("/associate")
    public ResponseEntity<String> associateToBreakfast(@Valid @RequestBody UserAssociationDTO dto) {
        service.associateUserToBreakfast(dto);
        return ResponseEntity.ok("Associação concluída com sucesso");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Café da manhã excluido com sucesso");
    }
}
