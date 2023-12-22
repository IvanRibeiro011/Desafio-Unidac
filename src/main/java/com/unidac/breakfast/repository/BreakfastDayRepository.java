package com.unidac.breakfast.repository;

import com.unidac.breakfast.entity.BreakfastDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakfastDayRepository extends JpaRepository<BreakfastDay, Long> {
}
