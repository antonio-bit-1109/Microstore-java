package org.example.microstoreprogetto.PRODUCTS.DTO;

public class StandardProductDTO {

    private String name;
    private String description;
    private String prezzo;
    private String category;
    private String stock;
    private Boolean is_active;

    public void setIs_active(Boolean is_active) {
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

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }


    public void setStock(String stock) {
        this.stock = stock;
    }

    public StandardProductDTO() {
    }

    //costrutt
    public StandardProductDTO(String name, String category, String prezzo, String Descrizione, String stock, boolean is_active) {
        setName(name);
        setCategory(category);
        setPrezzo(prezzo);
        setDescription(Descrizione);
        setStock(stock);
        setIs_active(is_active);
    }


    public Boolean getIs_active() {
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
