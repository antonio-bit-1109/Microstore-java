package org.example.microstoreprogetto.ORDERS.entity;

import jakarta.persistence.*;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private Long user_id;
    private String status;
    private Double total;
    private Timestamp created_at;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order_Items> orderItemsList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public void setId(Long id) {
        this.id = id;
    }

//    public void setUser_id(Long user_id) {
//        this.user_id = user_id;
//    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

//    public Long getUser_id() {
//        return user_id;
//    }

    public Double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getUser() {
        return user;
    }

    public void setOrderItemsList(List<Order_Items> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public List<Order_Items> getOrderItemsList() {
        return orderItemsList;
    }
}
