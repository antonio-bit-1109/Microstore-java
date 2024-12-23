package org.example.microstoreprogetto.USERS.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class LoginDTO {

//    @NotNull(message = "il campo id deve essere presente")
//    private Long id;

    //    @Email(message = "email deve essere in un formato aaa@aaa.it")
    @NotBlank(message = "email obbligatoria.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email should be valid")
    private String email;

    @NotBlank(message = "password non fornita")
    private String password;


    public void setPassword(String password) {
        this.password = password;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

//    public Long getId() {
//        return id;
//    }
}
