package org.example.microstoreprogetto.ORDERS.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.microstoreprogetto.ORDERS.DTO.CreateOrderDTO;
import org.example.microstoreprogetto.ORDERS.DTO.EditOrdineDTO;
import org.example.microstoreprogetto.ORDERS.DTO.StandardOrderDTO;
import org.example.microstoreprogetto.ORDERS.repository.OrderRepository;
import org.example.microstoreprogetto.ORDERS.service.OrderService;
import org.example.microstoreprogetto.USERS.service.UserServices;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.customResponse.general.MessageResp;
import org.example.microstoreprogetto.util.customResponse.order.OrderMsgResponse;
import org.hibernate.query.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderMsgResponse> createNewOrder(@Valid @RequestBody CreateOrderDTO orderData) {

        try {

            BaseDTO order = this.orderService.creazioneOrdine(orderData);
            return new ResponseEntity<>(new OrderMsgResponse(order, "ordine creato con successo"), HttpStatus.OK);

        } catch (RuntimeException |
                 InvocationTargetException |
                 NoSuchMethodException |
                 IllegalAccessException |
                 InstantiationException ex) {
            return new ResponseEntity<>(new OrderMsgResponse(null, "errore durante la creazione dell'ordine: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/edit")
    public ResponseEntity<OrderMsgResponse> editOrdine(@Valid @RequestBody EditOrdineDTO editOrder) {

        // se lo stato dell'ordine è shipped allora non puoi far modifiche.
        try {


            BaseDTO orderDTO = orderService.ModificaOrdine(editOrder);
            return new ResponseEntity<>(new OrderMsgResponse(orderDTO, "ordine modificato con successo"), HttpStatus.OK);

        } catch (RuntimeException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException ex) {

            return new ResponseEntity<>(new OrderMsgResponse(null, "errore durante la modifica dell'ordine: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    // COMPLETA I PROCESSAMENTI ORDINE

    @GetMapping("/StatusProcessing/{id}")
    public ResponseEntity<MessageResp> StatoOrdineProcessing(@NotNull @PathVariable Long id) {
        try {
            String res = orderService.StatusProcessing(id);
            return new ResponseEntity<>(new MessageResp(res), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new MessageResp("errore durante la modifica dell'ordine allo stato di 'processamento': " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/StatusShipped/{id}")
    public ResponseEntity<MessageResp> StatoOrdineShipped(@NotNull @PathVariable Long id) {
        try {

            String res = orderService.StatusShipped(id);
            return new ResponseEntity<>(new MessageResp(res), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new MessageResp("errore durante la modifica dell'ordine allo stato 'Shipped': " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/StatusCompleted/{id}")
    public ResponseEntity<MessageResp> StatoOrdineCompleted(@NotNull @PathVariable Long id) {
        try {

            String res = orderService.StatusCompleted(id);
            return new ResponseEntity<>(new MessageResp(res), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new MessageResp("errore durante la modifica dell'ordine allo stato 'Completato': " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
