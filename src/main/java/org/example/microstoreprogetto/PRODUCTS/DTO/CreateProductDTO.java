package org.example.microstoreprogetto.PRODUCTS.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateProductDTO {

    @NotBlank(message = "nome obbligatorio")
    private String name;

    private String description;

    @NotNull(message = "prezzo obbligatorio")
    private Float price;

    @NotNull(message = "URL immagine obbligatorio")
    private String image_url;


    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

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
