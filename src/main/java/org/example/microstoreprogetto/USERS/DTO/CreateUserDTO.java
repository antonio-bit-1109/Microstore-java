package org.example.microstoreprogetto.USERS.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateUserDTO {

    @NotBlank(message = "nome obbligatorio.")
    private String name;

    @NotBlank(message = "password obbligatoria.")
    @Size(min = 5, message = "la password deve essere lunga almeno 5 caratteri")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).+$", message = "La password deve contenere almeno un carattere speciale.")
    private String password;

    @NotBlank(message = "email obbligatoria.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email should be valid")
    private String email;

    //    private String role;
    @Size(min = 10, max = 10, message = "Il numero di telefono deve essere lungo esattamente 10 caratteri.")
    private String phone;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

//    public String getRole() {
//        return role;
//    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
