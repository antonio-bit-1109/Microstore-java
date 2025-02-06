package org.example.microstoreprogetto.CARTS.service;


import org.example.microstoreprogetto.CARTS.DTO.CreateCarrelloDTO;
import org.example.microstoreprogetto.CARTS.DTO.DeleteCartDTO;
import org.example.microstoreprogetto.CARTS.DTO.getCart.CartGET_DTO;
import org.example.microstoreprogetto.CARTS.entity.Cart_items;
import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.example.microstoreprogetto.CARTS.repository.CartRepository;
import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.PRODUCTS.util.UtilityProduct;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.USERS.repository.UserRepository;
import org.example.microstoreprogetto.util.base_dto.BasedDTO_GET;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;
import org.example.microstoreprogetto.util.configuration.mapperutils.Mapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    public BasedDTO_GET GetCarrello(Long idCart) {

        Optional<Carts> optCart = cartRepository.findById(idCart);

        if (optCart.isEmpty()) {
            throw new RuntimeException("il carrello selezionato non esiste.");
        }

        Carts carrello = optCart.get();

        // return this.mapper.toDTO(carrello, new StandardCartDTO());
        return this.mapper.GeneralMethodToCastEntityToGetDTO(carrello);
    }

    // i'm taking all the user's carts.
    public List<BasedDTO_GET> GetCarrelliUtente(Long idUser) {

        Optional<Users> utenteOpt = userRepository.findById(idUser);

        if (utenteOpt.isEmpty()) {
            throw new RuntimeException("utente selezionato non esiste.");
        }

        Users utente = utenteOpt.get();

        List<Optional<Carts>> listaCarrOpt = this.cartRepository.findCartsByUser(utente);

        if (listaCarrOpt.isEmpty()) {
            throw new RuntimeException("nessun carrello da mostrare.");
        }


        // se il singolo elemento all interno dell'optional esiste,
        // allora lo pusho dentro una lista di entity Carts.
        List<Carts> listaCarrelliUtente = new ArrayList<>();
        List<BasedDTO_GET> listaCarrelliDTO = new ArrayList<>();

        // aggiungo l 'elemento all interno della lista di Carts
        for (Optional<Carts> carrItem : listaCarrOpt) {

            if (carrItem.isPresent()) {
                listaCarrelliUtente.add(carrItem.get());
            }

        }

        // ciclo la lista di Carts e passo ogni oggetto ad un mapper che ritorna un oggetto CartGet_DTO
        // che pusho in listaCarrelloDTO
        // che poi ritorno al controller
        for (Carts cart : listaCarrelliUtente) {

            BasedDTO_GET cartDTO = this.mapper.GeneralMethodToCastEntityToGetDTO(cart);
            listaCarrelliDTO.add(cartDTO);
        }

        return listaCarrelliDTO;

    }

//    public Void AddToAlreadyExistedCart(AddProdTCartExistedDTO datiAggiuntaProdotto) {
//
//        Optional<Carts> carrelloOpt = cartRepository.findById(datiAggiuntaProdotto.getIdCarrello());
//
//        if (carrelloOpt.isEmpty()) {
//            throw new RuntimeException("impossibile trovare il carrello specificato.");
//        }
//
//        Carts carrello = carrelloOpt.get();
//
//        if (!(carrello.getUser().getId().equals(datiAggiuntaProdotto.getIdUser()))) {
//
//            throw new RuntimeException("L'utente associato al carrello non risulta essere lo stesso. ERRORE!");
//
//        } else {
//
//
//        }
//
//
//    }
}
