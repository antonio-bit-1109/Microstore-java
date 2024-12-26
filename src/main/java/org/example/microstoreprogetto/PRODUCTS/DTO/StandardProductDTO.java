package org.example.microstoreprogetto.PRODUCTS.DTO;

import org.example.microstoreprogetto.util.base_dto.BaseDTO;

public class StandardProductDTO extends BaseDTO {

    private String name;
    private String description;
    private String prezzo;
    private String category;
    private String stock;
    private String is_active;

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String prezzo) {
        this.prezzo = prezzo;
    }


    public void setStock(String stock) {
        this.stock = stock;
    }

    public StandardProductDTO() {
    }

    //costrutt
    public StandardProductDTO(String name, String category, String prezzo, String Descrizione, String stock, String is_active) {
        setName(name);
        setCategory(category);
        setPrice(prezzo);
        setDescription(Descrizione);
        setStock(stock);
        setIs_active(is_active);
    }


    public String getIs_active() {
        return is_active;
    }

    public String getCategory() {
        return category;
    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public String getPrezzo() {
        return prezzo;
    }


    public String getStock() {
        return stock;
    }
}
