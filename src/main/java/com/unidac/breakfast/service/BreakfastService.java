package com.unidac.breakfast.service;

import com.unidac.breakfast.dtos.request.BreakfastAssociationDTO;
import com.unidac.breakfast.dtos.request.BreakfastDayInsertDTO;
import com.unidac.breakfast.dtos.request.ItemInsertDTO;
import com.unidac.breakfast.dtos.request.UserAssociationDTO;
import com.unidac.breakfast.dtos.response.BreakfastDayDTO;
import com.unidac.breakfast.entity.BreakfastDay;
import com.unidac.breakfast.entity.BreakfastItem;
import com.unidac.breakfast.entity.User;
import com.unidac.breakfast.exceptions.ItemAlreadyRegisteredException;
import com.unidac.breakfast.exceptions.ResourceNotFoundException;
import com.unidac.breakfast.repository.BreakfastDayRepository;
import com.unidac.breakfast.repository.BreakfastItemRepository;
import com.unidac.breakfast.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.unidac.breakfast.messages.Constants.*;

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
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(BREAKFAST_NOT_FOUND));
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
        BreakfastDay day = repository.searchByDate(dto.getDate()).orElseThrow(() -> new ResourceNotFoundException(BREAKFAST_NOT_FOUND));
        List<User> users = userRepository.searchUsersById(dto.getItems().stream().map(ItemInsertDTO::getCollaboratorId).toList());
        associateDayWithUsers(users, day);
        if (!users.isEmpty()) {
            verifyItemsAndInsert(day, dto.getItems());
        }
    }

    @Transactional
    public void associateItem(BreakfastAssociationDTO dto) {
        BreakfastDay day = repository.searchByDate(dto.getDate()).orElseThrow(() -> new ResourceNotFoundException(BREAKFAST_NOT_FOUND));
        List<BreakfastItem> savedItems = itemRepository.searchAllItems();
        BreakfastItem item = itemRepository.searchById(dto.getItemId()).orElseThrow(() -> new ResourceNotFoundException(ITEM_NOT_FOUND));
        savedItems.forEach(
                i -> {
                    if (itemRepository.verifyItemBynameAndDate(item.getName(), day.getDate())) {
                        throw new ItemAlreadyRegisteredException("O item " + i.getName() + " já foi registrado , favor escolher outra opção");
                    } else {
                        itemRepository.updateItem(dto.getItemId(), i.getName(), i.getMissing(), dto.getCollaboratorId(), day.getId());
                    }
                }
        );
    }

    @Transactional
    public void associateUserToBreakfast(UserAssociationDTO dto) {
        User user = userRepository.searchById(dto.getCollaboratorId()).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        BreakfastDay day = repository.searchByDate(dto.getDate()).orElseThrow(() -> new ResourceNotFoundException(BREAKFAST_NOT_FOUND));
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
            }
            User user = userRepository.searchById(dto.getCollaboratorId())
                    .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
            itemRepository.insert(dto.getName(), dto.getMissing(), user.getId(), day.getId());
        });
    }

    private void associateDayWithUsers(List<User> users, BreakfastDay day) {
        for (User u : users) {
            User user = userRepository.searchById(u.getId()).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
            repository.associateDayWithUser(day.getId(), user.getId());
        }
    }

    @Transactional
    public void update(Long id, BreakfastDayInsertDTO dto) {
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(BREAKFAST_NOT_FOUND));
        repository.updateBreakfast(day.getId(), dto.getDate());
    }

    @Transactional
    public void delete(Long id) {
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(BREAKFAST_NOT_FOUND));
        day.getItems().forEach(i -> itemRepository.deleteItem(i.getId()));
        repository.unAssociateBreakfastAndUsers(day.getId());
        repository.deleteBreakfast(day.getId());
    }
}
