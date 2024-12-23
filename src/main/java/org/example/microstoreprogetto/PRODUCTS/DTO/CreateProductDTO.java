package org.example.microstoreprogetto.PRODUCTS.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateProductDTO {

    @NotBlank(message = "nome obbligatorio")
    private String name;

    private String description;

    @NotNull(message = "prezzo obbligatorio")
    private Float price;

//    private String category;

//    private Integer Stock;


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

//    public void setStock(Integer stock) {
//        Stock = stock;
//    }

//    public void setCategory(String category) {
//        this.category = category;
//    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

//    public String getCategory() {
//        return category;
//    }

    public String getDescription() {
        return description;
    }

//    public Integer getStock() {
//        return Stock;
//    }


    public String normalizedName() {
        return getName().replaceAll(" ", "");
    }
}
