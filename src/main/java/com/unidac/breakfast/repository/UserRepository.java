package com.unidac.breakfast.repository;

import com.unidac.breakfast.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true
            , value = "SELECT * FROM tb_users WHERE tb_users.id = :id")
    Optional<User> searchById(Long id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM tb_users")
    List<User> searchAllUsers();

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO tb_users (name,cpf) VALUES (:name,:cpf)")
    void insert(String name, String cpf);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_users  SET name = :name , cpf = :cpf WHERE id = :id")
    void updateUser(Long id, String name, String cpf);

    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE FROM tb_users WHERE id = :id")
    void deleteUser(Long id);
}
