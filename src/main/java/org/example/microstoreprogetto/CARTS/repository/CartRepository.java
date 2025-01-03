package org.example.microstoreprogetto.CARTS.repository;

import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {
    Long id(Long id);

    List<Optional<Carts>> findCartsByUser(Users user);
}
