package org.example.microstoreprogetto.USERS.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.microstoreprogetto.USERS.DTO.CreateUserDTO;
import org.example.microstoreprogetto.USERS.DTO.EditUserDTO;

import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.USERS.service.UserServices;


import org.example.microstoreprogetto.util.configuration.webautenticationdetails.CustomWebAuthenticationDetails;
import org.example.microstoreprogetto.util.customResponse.general.MessageResp;
import org.example.microstoreprogetto.util.customResponse.user.UserMessageResponse;
import org.example.microstoreprogetto.util.enums.roles.ROLES;
import org.example.microstoreprogetto.util.extractfromcontextholderspringsecurity.ExtractDataFromSecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;
import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAnyRole;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;
    private final ExtractDataFromSecurityContextHolder extractDataFromSecurityContextHolder;

    //costrutt
    public UserController(UserServices userServices, ExtractDataFromSecurityContextHolder extractDataFromSecurityContextHolder) {
        this.userServices = userServices;
        this.extractDataFromSecurityContextHolder = extractDataFromSecurityContextHolder;
    }


    // creazione nuovo utente
    @PostMapping("/register")
    public ResponseEntity<UserMessageResponse> CreateUser(@Valid @RequestBody CreateUserDTO userData) {

        try {

            StandardUserDTO user = userServices.RegistrationSave(userData);

            UserMessageResponse usmsResp = new UserMessageResponse(user, "utente creato con successo");
            return new ResponseEntity<>(usmsResp, HttpStatus.OK);

        } catch (RuntimeException e) {

            UserMessageResponse usmsResp = new UserMessageResponse(null, "errore durante la creazione dell'utente. " + e);
            return new ResponseEntity<>(usmsResp, HttpStatus.NOT_FOUND);
        }

    }

    // edit caratteristiche utente
    @PostMapping("/edit")
    public ResponseEntity<UserMessageResponse> EditUser(@Valid @RequestBody EditUserDTO editData) {

        try {

            Optional<Users> optionalUser = userServices.findUserbyId(editData.getId());
            StandardUserDTO returnedUser = userServices.CheckAndMapUser(optionalUser, editData);
            return new ResponseEntity<>(new UserMessageResponse(returnedUser, "utente modificato con successo."), HttpStatus.OK);

        } catch (RuntimeException e) {

            return new ResponseEntity<>(new UserMessageResponse(null, "errore durante la modifica dei dati utente: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // soft delete utente (isactive = false)
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MessageResp> SoftDelete(@PathVariable Long id) {
        try {

            Optional<Users> optionalUser = userServices.findUserbyId(id);

            userServices.softDelete(optionalUser);
            return new ResponseEntity<>(new MessageResp("utente cancellato con successo."), HttpStatus.OK);


        } catch (RuntimeException ex) {
            return new ResponseEntity<>(new MessageResp("errore durante la cancellazione dell'utente: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // riattivazione account (isactive = true)
    @GetMapping("/reactivated/{id}")
    public ResponseEntity<MessageResp> reactivateAccount(@PathVariable Long id) {

        try {

            Optional<Users> user = userServices.findUserbyId(id);
            int res = userServices.Reactivate(user);

            return switch (res) {
                case 0 -> new ResponseEntity<>(new MessageResp("nessun utente trovato."), HttpStatus.NOT_FOUND);
                case 1 ->
                        new ResponseEntity<>(new MessageResp("L'utente selezionato risulta già attivo."), HttpStatus.OK);
                case 2 -> new ResponseEntity<>(new MessageResp("Account riattivato con successo."), HttpStatus.OK);
                default -> throw new RuntimeException("risposta imprevista dal servizio di riattivazione dell utente.");
            };

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new MessageResp("errore durante la reattivazione dell'utente: " + ex), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // posso accedere a questo endpoint solo se sto facendo la get dei dati del mio stesso account
    @GetMapping("/get/{id}")
    public ResponseEntity<UserMessageResponse> getUtente(@NotNull @PathVariable Long id) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // il details arriva come un unica mega stringa contenente
            // [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null, UserId=22]
            // lo passo ad un metodo per estrarre la stringa che mi serve
            CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();

            String idUser = this.extractDataFromSecurityContextHolder.ExtractIdUser(details.toString());


            // se l'id utente ricavato dal token non è uguale all' id inviato tramite request all endpoint significa che
            // qualcuno sta chiedendo dati di un altro utente
            // solo un admin puo fare questa cosa
            if (!idUser.equals(id.toString())) {
                throw new RuntimeException("stai cercando di modificare i dati di un altro utente. Impossibile proseguire.");
            }

            StandardUserDTO user = userServices.TrovaUtente(id);
            return new ResponseEntity<>(new UserMessageResponse(user, "utente trovato con successo"), HttpStatus.OK);

        } catch (RuntimeException ex) {
            return new ResponseEntity<>(new UserMessageResponse(null, "errore durante il reperimento dei dati. " + ex), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
