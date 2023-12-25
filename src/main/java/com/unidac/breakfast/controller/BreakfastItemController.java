package com.unidac.breakfast.controller;

import com.unidac.breakfast.dtos.response.BreakfastItemDTO;
import com.unidac.breakfast.service.BreakfastItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class BreakfastItemController {

    private final BreakfastItemService service;

    public BreakfastItemController(BreakfastItemService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<BreakfastItemDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<BreakfastItemDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody BreakfastItemDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Item alterado com sucesso");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Item excluido com sucesso");
    }
}
