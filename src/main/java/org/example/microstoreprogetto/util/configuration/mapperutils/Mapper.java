package org.example.microstoreprogetto.util.configuration.mapperutils;


import org.example.microstoreprogetto.CARTS.DTO.getCart.CartGET_DTO;
import org.example.microstoreprogetto.CARTS.DTO.getCart.CartItemsDTO;
import org.example.microstoreprogetto.CARTS.entity.Cart_items;
import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.ORDERS.DTO.getOrder.OrderGET_DTO;
import org.example.microstoreprogetto.ORDERS.DTO.getOrder.OrderItemsDTO;
import org.example.microstoreprogetto.ORDERS.entity.Order_Items;
import org.example.microstoreprogetto.ORDERS.entity.Orders;
import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.PRODUCTS.repository.ProductRepository;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.base_dto.BasedDTO_GET;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


// invece di creare un DTO nel servizio da utilizzare per ritornare l'entita nel controller, mappo l oggetto

//prodotto qui dentro e lo ritorno, pronto per essere ritornato, gia con tutti i valori settati.
@Component
public class Mapper implements GenericMapper<BaseDTO, BaseEntity, BasedDTO_GET> {

    private final ProductRepository productRepository;
    //private final MethodValidationPostProcessor methodValidationPostProcessor;

    public Mapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
        //  this.methodValidationPostProcessor = methodValidationPostProcessor;
    }


    // serve per creare gli oggetti Order_items, ovvero la lista dei prodotti presenti nell 'ordine
    public List<? extends BaseEntity> MapperToListType(

            ArrayList<ProductInfoDTO> listaProdotti,
            Class<? extends BaseEntity> ResultEntity

    )
            throws RuntimeException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {


        List<BaseEntity> listaEntity = new ArrayList<>();

        // prendo ogni prodotto passato e controllo che esista tramite id
        for (ProductInfoDTO prodotto : listaProdotti) {

            Optional<Products> optionalProdottoEntity = productRepository.findById(Long.parseLong(prodotto.getIdProd()));

            if (optionalProdottoEntity.isEmpty()) {
                throw new RuntimeException("errore nel reperimento del prodotto entity");
            }

            Products prod = optionalProdottoEntity.get();

            // creo un istanza della resultEntity
            BaseEntity resEntity = ResultEntity.getDeclaredConstructor().newInstance();

            // METODO FACILE
            // capisco quale sia l'entity che mi serve per questo caso, la casto,
            // la riempio con gli oggetti giusti
            // e la ritorno.
//            if (resEntity.getClass().getName().toLowerCase().contains(".order_item")) {
            if (resEntity instanceof Order_Items) {

                CastToOrderItemEntity(resEntity, prod, prodotto, listaEntity);

            }

            if (resEntity instanceof Cart_items) {

                CastToCartItemsEntity(resEntity, prod, prodotto, listaEntity);

            }

        }
        return listaEntity;

    }

    private void CastToOrderItemEntity(BaseEntity resEntity, Products prod, ProductInfoDTO prodotto, List<BaseEntity> listaEntity) {

        Order_Items entityOrdItems = (Order_Items) resEntity;
        entityOrdItems.setQuantity((float) prodotto.getQuantity());
        entityOrdItems.setPrice(prodotto.getPrezzoUnitario().floatValue());
        entityOrdItems.setProduct(prod);
        listaEntity.add(entityOrdItems);
    }

    private void CastToCartItemsEntity(BaseEntity resEntity, Products prod, ProductInfoDTO prodotto, List<BaseEntity> listaEntity) {

        Cart_items entityCartItems = (Cart_items) resEntity;
        entityCartItems.setProduct(prod);
        entityCartItems.setQuantity(prodotto.getQuantity());

//        entityOrdItems.setQuantity((float) prodotto.getQuantity());
//        entityOrdItems.setPrice(prodotto.getPrezzoUnitario().floatValue());
//        entityOrdItems.setProduct(prod);
        listaEntity.add(entityCartItems);
    }


    // controlla che la lista passata sia effettivamente composta da tutti oggetti cart_items
    private boolean isItemCartItem(List<Object> listaOggetti) {

        for (Object item : listaOggetti) {

            if (!(item instanceof Cart_items)) {
                return false;

            }

        }
        return true;
    }


    // il metodo accetta un entity di tipo BaseEntity , tutte le entità estendono baseEntity
    // il secondo parametro è la classe che voglio ritornare come DTO al cnotroller , tutti i DTO estendono BaseDTO
    @Override
    public BaseDTO toDTO(BaseEntity entity, BaseDTO dtoClass) throws RuntimeException {
        try {

            // creo una mappa che conterrà tutti i metodi get dell entity con relativo valore.
            Map<String, Object> mappaMetodiGetEntity = new HashMap<>();


            //estraggo tutti i metodi dell entity
            Method[] MethodsEntity = entity.getClass().getDeclaredMethods();

            // se il metodo è un getter pusho nome-valore getter nella mappa
            for (Method metodo : MethodsEntity) {
                if (metodo.getName().startsWith("get")) {
                    Object ValueMetodo = metodo.invoke(entity);

                    if (ValueMetodo != null) {

                        // se il valore del metodo getter preso dall'entity non è una semplice stringa ma un oggetto
                        // devo mantenere l'oggetto e non castarlo ad stringa
                        // CONTROLLO DA FARE SU TUTTI GLI OGGETTI CHE POSSONO ESSERE PRESENTI NELL ENTITà COME DATO
                        if (ValueMetodo instanceof Users) {
                            mappaMetodiGetEntity.put(metodo.getName(), ValueMetodo);
                            continue;
                        }

                        // se il valore del metodo è un oggetto di tipo Cart_items non prendo il suo valore in stringa ma lo casto ad oggetto di tipo Cart_items
                        // e lo metto nella mappa.
                        if (ValueMetodo instanceof List<?>) {

                            mappaMetodiGetEntity.put(metodo.getName(), ValueMetodo);
                            continue;

                        }

                        // se il valore preso dal get del metodo è un long lo salvo come tale nella mappa
                        // e non lo casto a string
                        if (ValueMetodo instanceof Long) {
                            mappaMetodiGetEntity.put(metodo.getName(), ValueMetodo);
                            continue;
                        }

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
                    for (Map.Entry<String, Object> entry1 : mappaMetodiGetEntity.entrySet()) {

                        if (nomeMetodo
                                .substring(3)
                                .equalsIgnoreCase(entry1.getKey()
                                        .substring(3))) {

                            metodoDTO.invoke(dtoClass, entry1.getValue());
                            break;
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


    // metodo per castare alla DTO che ritorna un cart da inviare al client
    // stesso anche per order GET DTO ??
    private CartGET_DTO CastEntityToGETCART_dto(Carts carrello) {
        CartGET_DTO cartDataDTO = new CartGET_DTO();

        cartDataDTO.setIdCarrello(carrello.getId());
        cartDataDTO.setCreatedAt(carrello.getCreated_at().toString());
        cartDataDTO.setOwner(carrello.getUser().getName());

        for (Cart_items item : carrello.getCartItems()) {

            CartItemsDTO itemDTO = new CartItemsDTO();

            itemDTO.setIdProdotto(item.getProduct().getId());

            StandardProductDTO prodDTO = new StandardProductDTO(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getProduct().getCategory(),
                    Float.toString(item.getProduct().getPrice()),
                    item.getProduct().getDescription(),
                    Integer.toString(item.getProduct().getStock()),
                    item.getProduct().getIs_active().toString(),
                    item.getProduct().getImage_url()
            );
            itemDTO.setProdotto(prodDTO);
            cartDataDTO.AddToListaProdotti(itemDTO);
        }

        return cartDataDTO;
    }

    private OrderGET_DTO CastEntityToGETORDER_dto(Orders ordine) {
        OrderGET_DTO orderDataDTO = new OrderGET_DTO();

        orderDataDTO.setIdOrdine(ordine.getId());
        orderDataDTO.setTotal(ordine.getTotal().toString());
        orderDataDTO.setOwner(ordine.getUser().getName());
        orderDataDTO.setStatus(ordine.getStatus());

        for (Order_Items item : ordine.getOrderItemsList()) {
            OrderItemsDTO itemDTO = new OrderItemsDTO();
            itemDTO.setIdProdotto(item.getProduct().getId());

            StandardProductDTO prodDTO = new StandardProductDTO(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getProduct().getCategory(),
                    Float.toString(item.getProduct().getPrice()),
                    item.getProduct().getDescription(),
                    Integer.toString(item.getProduct().getStock()),
                    item.getProduct().getIs_active().toString(),
                    item.getProduct().getImage_url());

            itemDTO.setProdotto(prodDTO);
            orderDataDTO.AddToListaProdotti(itemDTO);
        }

        return orderDataDTO;
    }

    // metodo di switch per capire quale sia l entità che sta arrivando, se Carts od Orders
    // e creare una DTO specifica da ritornare al client.
    @Override
    public BasedDTO_GET GeneralMethodToCastEntityToGetDTO(BaseEntity entity) {

        // controllo di che tipo è l'entity passata
        if (entity instanceof Carts) {
            return CastEntityToGETCART_dto((Carts) entity);
        }

        if (entity instanceof Orders) {
            return CastEntityToGETORDER_dto((Orders) entity);
        }

        throw new RuntimeException("le entità passate non possono essere trasformate in entità specifiche.");
    }

}