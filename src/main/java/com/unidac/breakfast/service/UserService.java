package com.unidac.breakfast.service;

import com.unidac.breakfast.dtos.request.UserInsertDTO;
import com.unidac.breakfast.dtos.response.UserDTO;
import com.unidac.breakfast.entity.User;
import com.unidac.breakfast.exceptions.ResourceNotFoundException;
import com.unidac.breakfast.repository.BreakfastItemRepository;
import com.unidac.breakfast.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.unidac.breakfast.messages.Constants.USER_NOT_FOUND;

@Service
public class UserService {
    private final UserRepository repository;
    private final BreakfastItemRepository itemRepository;

    public UserService(UserRepository repository, BreakfastItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
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
        User user = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        itemRepository.deleteItemWithUserId(user.getId());
        repository.unAssociateUsersFromBreakfast(user.getId());
        repository.deleteUser(user.getId());
    }

}
