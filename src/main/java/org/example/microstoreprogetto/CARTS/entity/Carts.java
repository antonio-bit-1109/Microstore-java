package org.example.microstoreprogetto.CARTS.entity;

import jakarta.persistence.*;
import org.example.microstoreprogetto.CART_ITEMS.entity.Cart_items;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Carts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long user_id;

    private Timestamp created_at;

    // relazione many to one tanti, cart, potenzialmente un solo utente.
    // la chiave esterna user_id viene gestita automaticamente. non è necessario specificarla.
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    //relazione uno a molti tra Carts e cart_items
    // un cart puo avere molti cart items
    @OneToMany(mappedBy = "carts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart_items> cartItems;


    public void setId(Long id) {
        this.id = id;
    }

//    public void setUser_id(Long user_id) {
//        this.user_id = user_id;
//    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

//    public Long getUser_id() {
//        return user_id;
//    }

    public Timestamp getCreated_at() {
        return created_at;
    }
}
