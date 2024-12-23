package org.example.microstoreprogetto.USERS.entity;

import jakarta.persistence.*;
import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.example.microstoreprogetto.ORDERS.entity.Orders;

import java.sql.Timestamp;
import java.util.List;

//@Table(schema = "users")
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
    private Timestamp created_at;
    private Boolean is_active;


    // relazione 1 user per molti Carrelli
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carts> carts;

    @OneToMany(mappedBy = "user")
    private List<Orders> ordersList;

    public void setCarts(List<Carts> carts) {
        this.carts = carts;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setIsActive(Boolean active) {
        is_active = active;
    }

    public Boolean getIsActive() {
        return is_active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public List<Carts> getCarts() {
        return carts;
    }

    //metodi custom
    public String normalizedPassword() {
        return getPassword().trim();
    }
}
