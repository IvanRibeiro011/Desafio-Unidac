package com.unidac.breakfast.repository;

import com.unidac.breakfast.entity.BreakfastItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_items WHERE id IN [:ids]")
    List<BreakfastItem> searchAllItemsByIds(List<Long> ids);

    @Query(nativeQuery = true,
            value = "SELECT i.* FROM tb_items AS i INNER JOIN tb_breakfast as b ON i.breakfast_id = b.id" +
                    " WHERE b.date < :date AND i.missing IS NULL ")
    List<BreakfastItem> searchMissingItemsByDay(LocalDate date);

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_items (name) VALUES (:name)")
    void insertItem(String name);

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_items (name,missing,collaborator_id,breakfast_id) VALUES (:name,:missing,:collaboratorId,:breakfastId)")
    void insert(String name, Boolean missing, Long collaboratorId, Long breakfastId);

    @Query(value = "SELECT CASE WHEN COUNT(bi) > 0 THEN true ELSE false END " +
            "FROM tb_items bi " +
            "JOIN tb_breakfast bd ON bi.breakfast_id = bd.id " +
            "WHERE LOWER(bi.name) = LOWER(:itemName) AND bd.date = :date", nativeQuery = true)
    boolean verifyItemBynameAndDate(
            String itemName,
            LocalDate date
    );

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM tb_items WHERE LOWER(name) = LOWER(:itemName)", nativeQuery = true)
    boolean verifyItemByname(
            String itemName
    );

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_items  SET name = :name " +
                    ", missing = :missing " +
                    ", collaborator_id = :collaboratorId" +
                    ", breakfast_id = :breakfastId WHERE id = :id")
    void updateItem(Long id, String name, Boolean missing, Long collaboratorId, Long breakfastId);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_items SET missing = TRUE WHERE id = :id")
    void updateMissingItem(Long id);

    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM tb_items WHERE id = :id")
    void deleteItem(Long id);

    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM tb_items WHERE collaborator_id = :id")
    void deleteItemWithUserId(Long id);
}
