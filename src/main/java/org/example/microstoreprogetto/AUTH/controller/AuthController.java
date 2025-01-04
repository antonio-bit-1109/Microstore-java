package org.example.microstoreprogetto.AUTH.controller;

import jakarta.validation.Valid;
import org.example.microstoreprogetto.AUTH.service.AuthService;
import org.example.microstoreprogetto.USERS.DTO.LoginDTO;
import org.example.microstoreprogetto.USERS.service.UserServices;
import org.example.microstoreprogetto.util.customResponse.general.MessageResp;
import org.example.microstoreprogetto.util.customResponse.user.DoubleStringMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    // login utente
    @PostMapping("/login")
    public ResponseEntity<DoubleStringMsg> login(@Valid @RequestBody LoginDTO loginData) {

        try {

            String JWTToken = authService.autenticate(loginData.getEmail(), loginData.getPassword());
            return new ResponseEntity<>(new DoubleStringMsg("login effettuato con successo", JWTToken), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new DoubleStringMsg("Errore durante il processo di Login: " + ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResp> logout(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        return new ResponseEntity<>(new MessageResp("logout effettuato con successo."), HttpStatus.OK);
    }
}
