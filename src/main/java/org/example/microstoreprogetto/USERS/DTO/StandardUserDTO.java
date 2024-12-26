package org.example.microstoreprogetto.USERS.DTO;

import org.example.microstoreprogetto.util.base_dto.BaseDTO;

public class StandardUserDTO extends BaseDTO {

    private String name;
    private String email;
    private String phone;
    private String isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String active) {
        isActive = active;
    }
}
