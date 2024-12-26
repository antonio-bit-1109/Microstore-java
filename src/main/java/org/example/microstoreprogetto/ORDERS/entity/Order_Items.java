package org.example.microstoreprogetto.ORDERS.entity;

import jakarta.persistence.*;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;

@Entity
public class Order_Items extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private Long order_id;
    //private Long product_id;
    private Float quantity;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Orders getOrder() {
        return order;
    }

    public Float getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public Products getProduct() {
        return product;
    }

    public Float getQuantity() {
        return quantity;
    }
}
