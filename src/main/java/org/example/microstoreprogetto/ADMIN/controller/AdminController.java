package org.example.microstoreprogetto.ADMIN.controller;

import org.example.microstoreprogetto.ADMIN.service.AdminService;
import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;
import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.USERS.service.UserServices;
import org.example.microstoreprogetto.util.customResponse.general.MessageResp;
import org.example.microstoreprogetto.util.customResponse.product.AllProductsAndMsg;
import org.example.microstoreprogetto.util.customResponse.user.AllUsersAndMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


// su questo controller saranno presenti solo le azioni che è possibile che faccia un admin.
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserServices userServices;
    private final AdminService adminService;

    public AdminController(UserServices userServices, AdminService adminService) {
        this.userServices = userServices;
        this.adminService = adminService;
    }

    // soft delete utente (isactive = false)
    @GetMapping("/delete-user/{id}")
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
    @GetMapping("/reactivated-user/{id}")
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

            return new ResponseEntity<>(new MessageResp("errore durante la reattivazione dell'utente: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get-all-users")
    public ResponseEntity<AllUsersAndMsg> getAllUtenti() {
        try {

            List<StandardUserDTO> listaUtenti = userServices.prendiTuttiUtenti();
            return new ResponseEntity<>(new AllUsersAndMsg(listaUtenti, "lista utenti ottenuta con successo."), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new AllUsersAndMsg(null, "errore durante la reattivazione dell'utente: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}
