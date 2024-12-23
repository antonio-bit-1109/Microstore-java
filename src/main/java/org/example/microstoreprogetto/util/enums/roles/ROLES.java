package org.example.microstoreprogetto.util.enums.roles;

public enum ROLES {


    ADMIN("admin"),
    USER("user");

    private final String role;

    private ROLES(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
