package org.example.microstoreprogetto.CARTS.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.aspectj.bridge.Message;
import org.example.microstoreprogetto.CARTS.DTO.CreateCarrelloDTO;
import org.example.microstoreprogetto.CARTS.DTO.DeleteCartDTO;
import org.example.microstoreprogetto.CARTS.DTO.StandardCartDTO;
import org.example.microstoreprogetto.CARTS.DTO.getCart.CartGET_DTO;
import org.example.microstoreprogetto.CARTS.service.CartService;
import org.example.microstoreprogetto.ORDERS.DTO.CreateOrderDTO;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.customResponse.cart.CartMsgDTO;
import org.example.microstoreprogetto.util.customResponse.general.MessageResp;
import org.example.microstoreprogetto.util.customResponse.order.OrderMsgResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create-new")
    public ResponseEntity<MessageResp> creazioneNuovoCarrello(@Valid @RequestBody CreateCarrelloDTO carrelloData) {
        try {

            String msg = this.cartService.CreaCarrello(carrelloData);
            return new ResponseEntity<>(new MessageResp(msg), HttpStatus.OK);

        } catch (RuntimeException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException ex) {

            return new ResponseEntity<>(new MessageResp("errore durante il salvataggio del carrello: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  cancella il carrello specificato tramite il suo id
    @PutMapping("/delete")
    public ResponseEntity<MessageResp> cancellaCarrello(@Valid @RequestBody DeleteCartDTO datiDelete) {
        try {

            this.cartService.CancellaCarrello(datiDelete);
            return new ResponseEntity<>(new MessageResp("carrello cancellato con successo."), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new MessageResp("errore durante la cancellazione del carrello selezionato: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // trova lo specifico carrello tramite id
    @GetMapping("/get/{idCart}")
    public ResponseEntity<CartMsgDTO> TrovaCarrello(@NotNull @PathVariable Long idCart) {
        try {

            CartGET_DTO cartDTO = this.cartService.GetCarrello(idCart);
            return new ResponseEntity<>(new CartMsgDTO(cartDTO, "carrello reperito con successo."), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (RuntimeException ex) {
            return new ResponseEntity<>(new CartMsgDTO(null, "errore durante il reperimento del carrello.: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // trova tutti i carrelli dell utente selezionato.
    @GetMapping("/get-all/{idUser}")
    public ResponseEntity<MessageResp> TrovaTuttiCarrelli() {
        try {

            CartGET_DTO cartDTO = this.cartService.GetCarrello(idCart);
            return new ResponseEntity<>(new CartMsgDTO(cartDTO, "carrello reperito con successo."), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (RuntimeException ex) {
            return new ResponseEntity<>(new CartMsgDTO(null, "errore durante il reperimento del carrello.: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
