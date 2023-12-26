package com.unidac.breakfast.service;

import com.unidac.breakfast.dtos.request.BreakfastDayInsertDTO;
import com.unidac.breakfast.dtos.request.ItemInsertDTO;
import com.unidac.breakfast.dtos.request.UserAssociationDTO;
import com.unidac.breakfast.dtos.response.BreakfastDayDTO;
import com.unidac.breakfast.entity.BreakfastDay;
import com.unidac.breakfast.entity.User;
import com.unidac.breakfast.exceptions.ItemAlreadyRegisteredException;
import com.unidac.breakfast.exceptions.ResourceNotFoundException;
import com.unidac.breakfast.repository.BreakfastDayRepository;
import com.unidac.breakfast.repository.BreakfastItemRepository;
import com.unidac.breakfast.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.unidac.breakfast.messages.Constants.breakfastNotFound;
import static com.unidac.breakfast.messages.Constants.userNotFound;

@Service
public class BreakfastService {
    private final BreakfastDayRepository repository;
    private final UserRepository userRepository;
    private final BreakfastItemRepository itemRepository;

    public BreakfastService(BreakfastDayRepository repository, UserRepository userRepository, BreakfastItemRepository itemRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public BreakfastDayDTO findById(Long id) {
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(breakfastNotFound));
        return new BreakfastDayDTO(day);
    }

    @Transactional(readOnly = true)
    public List<BreakfastDayDTO> findAll() {
        List<BreakfastDay> breakfasts = repository.searchAllUsers();
        return breakfasts.stream().map(BreakfastDayDTO::new).toList();
    }

    @Transactional
    public void insert(BreakfastDayInsertDTO dto) {
        repository.insert(dto.getDate());
        BreakfastDay day = repository.searchByDate(dto.getDate()).orElseThrow(() -> new ResourceNotFoundException(breakfastNotFound));
        List<User> users = userRepository.searchUsersById(dto.getItems().stream().map(ItemInsertDTO::getCollaboratorId).toList());
        associateDayWithUsers(users, day);
        if (!users.isEmpty()) {
            verifyItemsAndInsert(day, dto.getItems());
        }
    }

    @Transactional
    public void associateUserToBreakfast(UserAssociationDTO dto) {
        User user = userRepository.searchById(dto.getCollaboratorId()).orElseThrow(() -> new ResourceNotFoundException(userNotFound));
        BreakfastDay day = repository.searchByDate(dto.getDate()).orElseThrow(() -> new ResourceNotFoundException(breakfastNotFound));
        dto.getItems().forEach(
                i -> {
                    if (itemRepository.verifyItemBynameAndDate(i.getName(), day.getDate())) {
                        throw new ItemAlreadyRegisteredException("O item " + i.getName() + " já foi registrado , favor escolher outra opção");
                    } else {
                        itemRepository.insert(i.getName(), i.getMissing(), user.getId(), day.getId());
                    }
                }
        );
    }

    private void verifyItemsAndInsert(BreakfastDay day, List<ItemInsertDTO> dtos) {
        dtos.forEach(dto -> {
            if (itemRepository.verifyItemBynameAndDate(dto.getName(), day.getDate())) {
                throw new ItemAlreadyRegisteredException("O item " + dto.getName() + " já foi registrado , favor escolher outra opção");
            } else {
                User user = userRepository.searchById(dto.getCollaboratorId())
                        .orElseThrow(() -> new ResourceNotFoundException(userNotFound));
                itemRepository.insert(dto.getName(), dto.getMissing(), user.getId(), day.getId());
            }
        });
    }

    private void associateDayWithUsers(List<User> users, BreakfastDay day) {
        for (User u : users) {
            User user = userRepository.searchById(u.getId()).orElseThrow(() -> new ResourceNotFoundException(userNotFound));
            repository.associateDayWithUser(day.getId(), user.getId());
        }
    }

    @Transactional
    public void update(Long id, BreakfastDayInsertDTO dto) {
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(breakfastNotFound));
        repository.updateBreakfast(day.getId(), dto.getDate());
    }

    @Transactional
    public void delete(Long id) {
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(breakfastNotFound));
        day.getItems().forEach(i -> itemRepository.deleteItem(i.getId()));
        repository.unAssociateBreakfastAndUsers(day.getId());
        repository.deleteBreakfast(day.getId());
    }
}
