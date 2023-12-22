package com.unidac.breakfast.repository;

import com.unidac.breakfast.entity.BreakfastItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreakfastItemRepository extends JpaRepository<BreakfastItem, Long> {
    @Query(nativeQuery = true
            , value = "SELECT * FROM tb_items WHERE tb_items.id = :id")
    Optional<BreakfastItem> searchById(Long id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_items")
    List<BreakfastItem> searchAllItems();

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_items (name,missing,colaborator_id) VALUES (:name,:missing,:collaboratorId)")
    void insert(String name, Boolean missing, Long collaboratorId);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_items  SET name = :name , cpf = :cpf WHERE id = :id")
    void updateItem(Long id, String name, String cpf);

    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM tb_items WHERE id = :id")
    void deleteItem(Long id);
}
