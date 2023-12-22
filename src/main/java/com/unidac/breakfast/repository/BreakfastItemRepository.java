package com.unidac.breakfast.repository;

import com.unidac.breakfast.entity.BreakfastItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakfastItemRepository extends JpaRepository<BreakfastItem, Long> {
}
