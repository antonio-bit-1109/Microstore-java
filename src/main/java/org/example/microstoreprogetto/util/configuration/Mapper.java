package org.example.microstoreprogetto.util.configuration;

import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.ORDERS.DTO.StandardOrderDTO;
import org.example.microstoreprogetto.ORDERS.entity.Order_Items;
import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.PRODUCTS.repository.ProductRepository;
import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// invece di creare un DTO nel servizio da utilizzare per ritornare l'entita nel controller, mappo l oggetto

//prodotto qui dentro e lo ritorno, pronto per essere ritornato, gia con tutti i valori settati.
@Component
public class Mapper {

    private final ProductRepository productRepository;

    public Mapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public StandardProductDTO MapperProductDto(String name, String category, String price, String description, String stock, Boolean isactive) {
        StandardProductDTO standardProduct = new StandardProductDTO();

        standardProduct.setName(name);
        standardProduct.setCategory(category);
        standardProduct.setPrezzo(price);
        standardProduct.setDescription(description);
        standardProduct.setStock(stock);
        standardProduct.setIs_active(isactive);
        return standardProduct;
    }

    public List<Order_Items> MapperToOrderListType(ArrayList<ProductInfoDTO> listaProdotti) throws RuntimeException {

        List<Order_Items> listaOrdiniItems = new ArrayList<>();

        for (ProductInfoDTO prodotto : listaProdotti) {

            Optional<Products> optionalProdottoEntity = productRepository.findById(Long.parseLong(prodotto.getIdProd()));

            if (optionalProdottoEntity.isEmpty()) {
                throw new RuntimeException("errore nel reperimento del prodotto entity");
            }

            Products prodottoEntity = optionalProdottoEntity.get();

            Order_Items orderItem = new Order_Items();
            // orderItem.setId(Long.parseLong(prodotto.getIdProd()));
            orderItem.setQuantity((float) prodotto.getQuantity());
            orderItem.setPrice(prodotto.getPrezzoUnitario().floatValue());
            orderItem.setProduct(prodottoEntity);
            listaOrdiniItems.add(orderItem);
        }
        return listaOrdiniItems;
    }


    public StandardOrderDTO mapperOrderDTO(Long idOrdine, String nameAcquirente, String status, Double totSpesa, Time currentTime) {
        StandardOrderDTO orderDTOResponse = new StandardOrderDTO();

        orderDTOResponse.setIdOrdine(idOrdine);
        orderDTOResponse.setUsernameAcquirente(nameAcquirente);
        orderDTOResponse.setStatus(status);
        orderDTOResponse.setTotalespesa(totSpesa);
        orderDTOResponse.setOraordine(currentTime);
        return orderDTOResponse;
    }

    public StandardUserDTO mapperUserDTO(String name, String email, String phone, Boolean isActive) {

        StandardUserDTO userDTOResp = new StandardUserDTO();
        userDTOResp.setName(name);
        userDTOResp.setEmail(email);
        userDTOResp.setPhone(phone);
        userDTOResp.setActive(isActive);
        return userDTOResp;
    }
}