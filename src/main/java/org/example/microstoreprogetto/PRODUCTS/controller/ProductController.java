package org.example.microstoreprogetto.PRODUCTS.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.microstoreprogetto.PRODUCTS.DTO.AddStockProductDTO;
import org.example.microstoreprogetto.PRODUCTS.DTO.CreateProductDTO;
import org.example.microstoreprogetto.PRODUCTS.service.ProductService;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.customResponse.general.MessageResp;

import org.example.microstoreprogetto.util.customResponse.product.AllProductsAndMsg;
import org.example.microstoreprogetto.util.customResponse.product.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // aggiunta nuovo prodotto
    @PostMapping("/insert")
    public ResponseEntity<MessageResp> insertProdotto(@Valid @RequestBody CreateProductDTO productData) {
        try {

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName();
//            Collection<? extends GrantedAuthority> ruoli = authentication.getAuthorities();

            this.productService.productCreation(productData);
            return new ResponseEntity<>(new MessageResp("prodotto creato con successo"), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new MessageResp("Errore durante la creazione del prodotto: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // aggiunta quantità stock al prodotto
    // ritorno mesg + eventuale prodotto modificato (utilizzando una DTO)
    @PostMapping("/addStock")
    public ResponseEntity<ProductResponse> addStockToProduct(@Valid @RequestBody AddStockProductDTO addStockData) {

        try {


            BaseDTO prod = this.productService.addStockQuantity(addStockData);
            return new ResponseEntity<>(new ProductResponse(prod, "quantità prodotto aggiornata con successo."), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new ProductResponse(null, "Errore durante l'aggiunta allo stock : " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    // get del prodotto
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getProdotto(@NotNull @PathVariable Long id) {
        try {

            BaseDTO prod = this.productService.getProduct(id);
            return new ResponseEntity<>(new ProductResponse(prod, "Prodotto recuperato con successo"), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new ProductResponse(null, "Errore durante la get del prodotto : " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    //delete prodotto
    @GetMapping("/delete/{id}")
    public ResponseEntity<MessageResp> softDeleteProdotto(@NotNull @PathVariable Long id) {

        try {

            this.productService.deleteProduct(id);
            return new ResponseEntity<>(new MessageResp("Prodotto cancellato con successo"), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new MessageResp("Errore durante la cancellazione del prodotto : " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<AllProductsAndMsg> getAllProducts() {
        try {

            List<BaseDTO> listaProdottiDTO = productService.GetTuttiProdotti();
            return new ResponseEntity<>(new AllProductsAndMsg(listaProdottiDTO, "prodotti ottenuti con successo"), HttpStatus.OK);

        } catch (RuntimeException ex) {

            return new ResponseEntity<>(new AllProductsAndMsg(null, "Errore durante la get di tutti i prodotti : " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
