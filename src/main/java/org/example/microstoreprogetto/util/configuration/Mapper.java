package org.example.microstoreprogetto.util.configuration;

import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.ORDERS.DTO.StandardOrderDTO;
import org.example.microstoreprogetto.ORDERS.entity.Order_Items;
import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.PRODUCTS.repository.ProductRepository;
import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;
import org.example.microstoreprogetto.util.configuration.mapperinterface.GenericMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.*;


// invece di creare un DTO nel servizio da utilizzare per ritornare l'entita nel controller, mappo l oggetto

//prodotto qui dentro e lo ritorno, pronto per essere ritornato, gia con tutti i valori settati.
@Component
public class Mapper implements GenericMapper<BaseDTO, BaseEntity> {

    private final ProductRepository productRepository;
    //private final MethodValidationPostProcessor methodValidationPostProcessor;

    public Mapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
        //  this.methodValidationPostProcessor = methodValidationPostProcessor;
    }


    public StandardProductDTO MapperProductDto(String name, String category, String price, String description, String stock, Boolean isactive) {
        StandardProductDTO standardProduct = new StandardProductDTO();

        standardProduct.setName(name);
        standardProduct.setCategory(category);
        standardProduct.setPrice(price);
        standardProduct.setDescription(description);
        standardProduct.setStock(stock);
        standardProduct.setIs_active(isactive.toString());
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
        userDTOResp.setIsActive(isActive.toString());
        return userDTOResp;
    }

    // il metodo accetta un entity di tipo BaseEntity , tutte le entità estendono baseEntity
    // il secondo parametro è la classe che voglio ritornare come DTO al cnotroller , tutti i DTO estendono BaseDTO
    @Override
    public BaseDTO toDTO(BaseEntity entity, BaseDTO dtoClass) throws RuntimeException {
        try {

            // creo una mappa che conterrà tutti i metodi get dell entity con relativo valore.
            Map<String, String> mappaMetodiGetEntity = new HashMap<>();


            //estraggo tutti i metodi dell entity
            Method[] MethodsEntity = entity.getClass().getDeclaredMethods();

            // se il metodo è un getter pusho nome-valore getter nella mappa
            for (Method metodo : MethodsEntity) {
                if (metodo.getName().startsWith("get")) {
                    Object ValueMetodo = metodo.invoke(entity);

                    if (ValueMetodo != null) {
                        mappaMetodiGetEntity.put(metodo.getName(), ValueMetodo.toString());
                    }
                }
            }

            // capisco quale DTO ho di fronte
            Class<?> ClassDTO = dtoClass.getClass();

            // estraggo tutti i metodi dal DTO
            Method[] metodiDTO = ClassDTO.getDeclaredMethods();


            // ciclo i metodi del DTO e prendo in considerazione solo i setter
            for (Method metodoDTO : metodiDTO) {
                String nomeMetodo = metodoDTO.getName();

                // invoco il setter della dto e gli passo come valore
                // il valore dell entity salvato nella dto che ha anche lui nel nome
                // lo stesso nome del getter dell entity (entity --> getName , dto --> setName )
                // faccio il substring del get/set e controllo che la seconda parte della stringa sia uguale,
                // ignorando upper e lower case
                // se le stringhe coicidono allora nel setter della DTO pusho il valore presente nella mappa,
                // che corrisponde al getter dell entity
                if (nomeMetodo.startsWith("set")) {

                    // ciclo la mappa dell entity  ...
                    for (Map.Entry<String, String> entry1 : mappaMetodiGetEntity.entrySet()) {

                        if (nomeMetodo
                                .substring(3)
                                .equalsIgnoreCase(entry1.getKey()
                                        .substring(3))) {

                            metodoDTO.invoke(dtoClass, entry1.getValue());
                        }
                    }


                }
            }

            // ritorno il dto da inviare poi al controller
            return dtoClass;

        } catch (RuntimeException |
                 InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException("errore nella conversione da Entity a DTO: " + e.getMessage());
        }
    }

}