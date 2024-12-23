package org.example.microstoreprogetto.util.enums.categoryproduct;

public enum CategoryProduct {

    GENERAL("general"),
    FOOD("food"),
    WEAPON("weapon");

    private final String desc;

    private CategoryProduct(String desc) {
        this.desc = desc;
    }

    public String getDescrizione() {
        return desc;
    }
}
