package com.unidac.breakfast.service;

import com.unidac.breakfast.dtos.request.BreakfastDayInsertDTO;
import com.unidac.breakfast.dtos.request.ItemInsertDTO;
import com.unidac.breakfast.dtos.request.UserAssociationDTO;
import com.unidac.breakfast.dtos.response.BreakfastDayDTO;
import com.unidac.breakfast.entity.BreakfastDay;
import com.unidac.breakfast.entity.BreakfastItem;
import com.unidac.breakfast.entity.User;
import com.unidac.breakfast.repository.BreakfastDayRepository;
import com.unidac.breakfast.repository.BreakfastItemRepository;
import com.unidac.breakfast.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new RuntimeException("Not found"));
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
        BreakfastDay day = repository.searchByDate(dto.getDate()).orElseThrow(() -> new RuntimeException("Café da manhã não encontrado"));
        List<User> users = userRepository.searchUsersById(dto.getItems().stream().map(ItemInsertDTO::getCollaboratorId).toList());
        associateDayWithUsers(users, day);
        if (!users.isEmpty()) {
            verifyItemsAndInsert(day, dto.getItems());
        }
    }

    @Transactional
    public void associateUserToBreakfast(UserAssociationDTO dto) {
        User user = userRepository.searchById(dto.getCollaboratorId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        BreakfastDay day = repository.searchByDate(dto.getDate()).orElseThrow(() -> new RuntimeException("Café da manhã não encontrado"));
        dto.getItems().forEach(
                i -> {
                    if (itemRepository.verifyItemBynameAndDate(i.getName(), day.getDate())) {
                        throw new RuntimeException("O item " + i.getName() + " já foi registrado , favor escolher outra opção");
                    } else {
                        itemRepository.insert(i.getName(), i.getMissing(), user.getId(), day.getId());
                    }
                }
        );
    }

    private void verifyItemsAndInsert(BreakfastDay day, List<ItemInsertDTO> dtos) {
        List<BreakfastItem> existingItems = itemRepository.searchItemsByDay(day.getDate());

        existingItems.forEach(existingItem -> {
            if (itemRepository.verifyItemBynameAndDate(existingItem.getName(), day.getDate())) {
                throw new RuntimeException("Item já existente para este café da manhã");
            } else {
                User user = userRepository.searchById(existingItem.getColaborator().getId())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
                itemRepository.insert(existingItem.getName(), existingItem.getMissing(), user.getId(), existingItem.getBreakfast().getId());
            }
        });

        dtos.forEach(dto -> {
            if (itemRepository.verifyItemBynameAndDate(dto.getName(), day.getDate())) {
                throw new RuntimeException("Não é permitido repetir itens na lista");
            } else {
                User user = userRepository.searchById(dto.getCollaboratorId())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
                itemRepository.insert(dto.getName(), dto.getMissing(), user.getId(), day.getId());
            }
        });
    }

    private void associateDayWithUsers(List<User> users, BreakfastDay day) {
        for (User u : users) {
            User user = userRepository.searchById(u.getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            repository.associateDayWithUser(day.getId(), user.getId());
        }
    }

    @Transactional
    public void update(Long id, BreakfastDayInsertDTO dto) {
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new RuntimeException("Café da manhã não encontrado"));
        repository.updateBreakfast(day.getId(), dto.getDate());
    }

    @Transactional
    public void delete(Long id) {
        BreakfastDay day = repository.searchById(id).orElseThrow(() -> new RuntimeException("Café da mnahã não encontrado"));
        repository.unAssociateBreakfastAndUsers(day.getId());
        repository.deleteBreakfast(day.getId());
    }
}
