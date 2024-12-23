package org.example.microstoreprogetto.USERS.repository;

import org.apache.catalina.User;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Transactional
    default void softDelete(Users user) {
        user.setIsActive(false);
        save(user);
    }

    @Transactional
    default void reactivateAccount(Users user) {
        user.setIsActive(true);

    }

    // query custom per la selezione tramite una query personalizzata
    @Query("SELECT count(id) FROM Users  WHERE email = :email")
    int existsByEmail(@Param("email") String email);


    Optional<Users> findByEmail(String email);
}
