package com.unidac.breakfast.service;

import com.unidac.breakfast.dtos.response.BreakfastItemDTO;
import com.unidac.breakfast.entity.BreakfastDay;
import com.unidac.breakfast.entity.BreakfastItem;
import com.unidac.breakfast.entity.User;
import com.unidac.breakfast.exceptions.ResourceNotFoundException;
import com.unidac.breakfast.repository.BreakfastDayRepository;
import com.unidac.breakfast.repository.BreakfastItemRepository;
import com.unidac.breakfast.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.unidac.breakfast.messages.Constants.ITEM_NOT_FOUND;
import static com.unidac.breakfast.messages.Constants.USER_NOT_FOUND;

@Service
public class BreakfastItemService {

    private final BreakfastItemRepository repository;
    private final UserRepository userRepository;
    private final BreakfastDayRepository breakfastRepository;

    public BreakfastItemService(BreakfastItemRepository repository, UserRepository userRepository, BreakfastDayRepository breakfastRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.breakfastRepository = breakfastRepository;
    }

    @Transactional(readOnly = true)
    public BreakfastItemDTO findById(Long id) {
        BreakfastItem item = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(ITEM_NOT_FOUND));
        return new BreakfastItemDTO(item);
    }

    @Transactional(readOnly = true)
    public List<BreakfastItemDTO> findAll() {
        List<BreakfastItem> items = repository.searchAllItems();
        List<BreakfastItemDTO> response = new ArrayList<>();
        for (BreakfastItem i : items) {
            if (i.getCollaborator() != null || i.getBreakfast() != null) {
                response.add(new BreakfastItemDTO(i));
            } else {
                response.add(new BreakfastItemDTO(i.getId(), i.getName(), i.getMissing(), null, null));
            }
        }
        return response;
    }

    @Transactional
    public void update(Long id, BreakfastItemDTO dto) {
        User user = userRepository.searchById(dto.getCollaboratorId()).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        BreakfastDay day = breakfastRepository.getReferenceById(dto.getBreakfastId());
        repository.updateItem(id, dto.getName(), dto.getMissing(), user.getId(), day.getId());
    }

    @Transactional
    public void delete(Long id) {
        BreakfastItem item = repository.searchById(id).orElseThrow(() -> new ResourceNotFoundException(ITEM_NOT_FOUND));
        repository.deleteItem(item.getId());
    }

    @Scheduled(cron = "0 0 7 * * ?")
    @Transactional
    public void verifyMissingItems() {
        List<BreakfastItem> missingItems = repository.searchMissingItemsByDay(LocalDate.now());
        missingItems.forEach(i -> repository.updateMissingItem(i.getId()));
    }


}
