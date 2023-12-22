package com.unidac.breakfast.controller;

import com.unidac.breakfast.dtos.request.UserInsertDTO;
import com.unidac.breakfast.dtos.response.UserDTO;
import com.unidac.breakfast.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<String> insert(@Valid @RequestBody UserInsertDTO dto) {
        service.insert(dto);
        return ResponseEntity.ok("Usuário criado com sucesso");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody UserInsertDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Usuário alterado com sucesso");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Usuário excluido com sucesso");
    }
}
