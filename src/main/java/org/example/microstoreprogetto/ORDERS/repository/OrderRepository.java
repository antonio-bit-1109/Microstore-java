package org.example.microstoreprogetto.ORDERS.repository;

import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.example.microstoreprogetto.ORDERS.entity.Orders;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Optional<Orders>> findOrdersByUser(Users user);
}
