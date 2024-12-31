package org.example.microstoreprogetto.CARTS.service;

import org.example.microstoreprogetto.CARTS.DTO.CreateCarrelloDTO;
import org.example.microstoreprogetto.CARTS.DTO.DeleteCartDTO;
import org.example.microstoreprogetto.CARTS.DTO.StandardCartDTO;
import org.example.microstoreprogetto.CARTS.DTO.getCart.CartGET_DTO;
import org.example.microstoreprogetto.CARTS.entity.Cart_items;
import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.example.microstoreprogetto.CARTS.repository.CartRepository;
import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.ORDERS.entity.Order_Items;
import org.example.microstoreprogetto.PRODUCTS.util.UtilityProduct;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.USERS.repository.UserRepository;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;
import org.example.microstoreprogetto.util.configuration.Mapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final UtilityProduct utilityProduct;

    public CartService(CartRepository cartRepository, UserRepository userRepository, Mapper mapper, UtilityProduct utilityProduct) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.utilityProduct = utilityProduct;
    }

    public String CreaCarrello(CreateCarrelloDTO carrelloDTO) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        // trovo user che sta salvando carrello

        Optional<Users> optUser = userRepository.findById(carrelloDTO.getIdUser());

        if (optUser.isEmpty()) {
            throw new RuntimeException("lo user selezionato non esiste.");
        }

        Users utente = optUser.get();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());


        // passo una lista nel primo parametro ed il tipo della lista nel secondo parametro
        // controlla esistenza dei prodotti che vengono inviati dal client
        // (controllo che id prodotto inviato dal client esista nel db)
        utilityProduct.CheckProductExistence(carrelloDTO.getListaProdotti(), ProductInfoDTO.class);


        //controllo che il prezzo del prodotto inviato dal client sia lo stesso prezzo del prodotto salvato su db
        utilityProduct.CheckPricesCorrispondation(carrelloDTO.getListaProdotti());

        Carts carrello = new Carts();

        //mappare i prodotti dal tipo ProductInfoDTO al tipo Order_items
        List<? extends BaseEntity> listaProd = mapper.MapperToListType(carrelloDTO.getListaProdotti(), Cart_items.class);


        // casto la lista generica di base entity specifica ad una lista di item di tipo cart_items.
        List<Cart_items> listaCartItems = listaProd.stream().map(item -> (Cart_items) item).toList();


        carrello.setUser(utente);
        carrello.setCreated_at(currentTime);
        carrello.setCartItems(listaCartItems);

        // per ogni cart_item setto il carrello di riferimento
        for (Cart_items item : listaCartItems) {
            item.setCarts(carrello);
        }

        cartRepository.save(carrello);

        // return this.mapper.MappedToStandardCartDTO(carrello);
        return "carrello salvato con successo.";
    }


    public void CancellaCarrello(DeleteCartDTO datiCart) throws RuntimeException {

        // controllo esistenza del carrello
        Optional<Carts> carrelloOpt = this.cartRepository.findById(datiCart.getIdCarrello());

        if (carrelloOpt.isEmpty()) {
            throw new RuntimeException("il carrello selezionato non esiste.");
        }

        Carts carrello = carrelloOpt.get();

        // controllo che il carrello selezionato sia effettivamente dell utente che ha inviato il JSON
        if (!(carrello.getUser().getId().equals(datiCart.getIdUser()))) {
            throw new RuntimeException("stai modificando un carrello che non ti appartiene. Contatta Assistenza.");
        }

        this.cartRepository.delete(carrello);

    }

    public CartGET_DTO GetCarrello(Long idCart) {

        Optional<Carts> optCart = cartRepository.findById(idCart);

        if (optCart.isEmpty()) {
            throw new RuntimeException("il carrello selezionato non esiste.");
        }

        Carts carrello = optCart.get();

        // return this.mapper.toDTO(carrello, new StandardCartDTO());
        return this.mapper.CastEntityToGETCART_dto(carrello);
    }
}
