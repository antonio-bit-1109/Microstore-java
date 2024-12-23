package org.example.microstoreprogetto.ORDERS.repository;

import org.example.microstoreprogetto.ORDERS.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

}
