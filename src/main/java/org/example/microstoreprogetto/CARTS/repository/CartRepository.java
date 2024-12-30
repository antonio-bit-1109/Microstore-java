package org.example.microstoreprogetto.CARTS.repository;

import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {
}
