package com.unidac.breakfast.service;

import com.unidac.breakfast.dtos.request.UserInsertDTO;
import com.unidac.breakfast.dtos.response.UserDTO;
import com.unidac.breakfast.entity.User;
import com.unidac.breakfast.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = repository.searchById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = repository.searchAllUsers();
        return users.stream().map(UserDTO::new).toList();
    }

    @Transactional
    public void insert(UserInsertDTO dto) {
        repository.insert(dto.getName(), dto.getCpf());
    }

    @Transactional
    public void update(Long id, UserInsertDTO dto) {
        repository.updateUser(id, dto.getName(), dto.getCpf());
    }

    @Transactional
    public void delete(Long id) {
        User user = repository.searchById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        repository.deleteUser(user.getId());
    }
}
