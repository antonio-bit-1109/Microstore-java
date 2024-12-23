package org.example.microstoreprogetto.USERS.DTO;

import jakarta.validation.constraints.*;

public class EditUserDTO {

    @NotNull
    private Long id;

    private String name;
    private String email;

    @Size(min = 5, message = "la password deve essere lunga almeno 5 caratteri")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).+$", message = "La password deve contenere almeno un carattere speciale.")
    private String password;

    private String phone;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public void setRole(String role) {
//        this.role = role;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

//    public String getRole() {
//        return role;
//    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
