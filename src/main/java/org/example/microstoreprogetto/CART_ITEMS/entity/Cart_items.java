package org.example.microstoreprogetto.CART_ITEMS.entity;


import jakarta.persistence.*;
import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;

@Entity
public class Cart_items extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private Long cart_id;

    //private Long product_id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Carts carts;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    public void setId(Long id) {
        this.id = id;
    }

//    public void setCart_id(Long cart_id) {
//        this.cart_id = cart_id;
//    }

//    public void setProduct_id(Long product_id) {
//        this.product_id = product_id;
//    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

//    public Long getCart_id() {
//        return cart_id;
//    }

//    public Long getProduct_id() {
//        return product_id;
//    }
}
