package com.unidac.breakfast.repository;

import com.unidac.breakfast.entity.BreakfastDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BreakfastDayRepository extends JpaRepository<BreakfastDay, Long> {
    @Query(nativeQuery = true
            , value = "SELECT * FROM tb_breakfast WHERE tb_breakfast.id = :id")
    Optional<BreakfastDay> searchById(Long id);

    @Query(nativeQuery = true
            , value = "SELECT * FROM tb_breakfast WHERE tb_breakfast.date = :date")
    Optional<BreakfastDay> searchByDate(LocalDate date);

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_breakfast")
    List<BreakfastDay> searchAllUsers();

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_breakfast (date) VALUES (:date)")
    void insert(LocalDate date);

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_breakfast_collaborator (breakfast_id,collaborator_id) VALUES (:dayId,:userId)")
    void associateDayWithUser(Long dayId, Long userId);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_breakfast SET date = :date WHERE id = :id")
    void updateBreakfast(Long id, LocalDate date);

    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM tb_breakfast WHERE id = :id")
    void deleteBreakfast(Long id);

    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM tb_breakfast_collaborator WHERE breakfast_id = :id")
    void unAssociateBreakfastAndUsers(Long id);
}
