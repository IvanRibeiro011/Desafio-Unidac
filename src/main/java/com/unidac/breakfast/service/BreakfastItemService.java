package com.unidac.breakfast.service;

import com.unidac.breakfast.dtos.response.BreakfastItemDTO;
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
        BreakfastItem item = repository.searchById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return new BreakfastItemDTO(item);
    }

    @Transactional(readOnly = true)
    public List<BreakfastItemDTO> findAll() {
        List<BreakfastItem> items = repository.searchAllItems();
        return items.stream().map(BreakfastItemDTO::new).toList();
    }

    @Transactional
    public void update(Long id, BreakfastItemDTO dto) {
        User user = userRepository.searchById(dto.getCollaboratorId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        BreakfastDay day = breakfastRepository.getReferenceById(dto.getBreakfastId());
        repository.updateItem(id, dto.getName(), dto.getMissing(), user.getId(), day.getId());
    }

    @Transactional
    public void delete(Long id) {
        BreakfastItem item = repository.searchById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        repository.deleteItem(item.getId());
    }
}
