package org.example.microstoreprogetto.ORDERS.service;

import org.example.microstoreprogetto.CARTS.entity.Carts;
import org.example.microstoreprogetto.ORDERS.DTO.CreateOrderDTO;
import org.example.microstoreprogetto.ORDERS.DTO.EditOrdineDTO;
import org.example.microstoreprogetto.ORDERS.DTO.StandardOrderDTO;
import org.example.microstoreprogetto.ORDERS.DTO.getOrder.OrderGET_DTO;
import org.example.microstoreprogetto.ORDERS.entity.Orders;
import org.example.microstoreprogetto.ORDERS.repository.OrderRepository;
import org.example.microstoreprogetto.ORDERS.entity.Order_Items;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.USERS.repository.UserRepository;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.base_dto.BasedDTO_GET;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;
import org.example.microstoreprogetto.util.configuration.CheckCalcoliOrder;
import org.example.microstoreprogetto.util.configuration.mapperutils.Mapper;
import org.example.microstoreprogetto.util.enums.statoordine.STATOORDINE;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CheckCalcoliOrder checkCalcoliOrder;
    private final Mapper mapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CheckCalcoliOrder checkCalcoliOrder, Mapper mapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.checkCalcoliOrder = checkCalcoliOrder;
        this.mapper = mapper;
    }

    public BaseDTO creazioneOrdine(CreateOrderDTO orderDTO) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

//        OGGETTO TIPO INVIATO DAL CLIENT:
//        {
//            "userId": 9,
//                "listaProdotti": [
//            {
//                "idProd": 4,
//                    "quantity": 2,
//                    "prezzoUnitario": 12.34
//            },
//            {
//                "idProd": 8,
//                    "quantity": 1,
//                    "prezzoUnitario": 14.54
//            }
//  ],
//            "total": 39.22
//        }

        // controllo che lo user esista
        Optional<Users> optionalUser = userRepository.findById(Long.parseLong(orderDTO.getUserId()));

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("l'utente selezionato per effettuare l'ordine non esiste.");
        }

        Users user = optionalUser.get();

        Time currentTime = new Time(System.currentTimeMillis());
        Timestamp currTime = new Timestamp(currentTime.getTime());

        //controllo che il prezzo effettivo all interno del json "total"
        // corrisponda al prodotto tra quantita e prezzo di ogni singolo prodotto
        checkCalcoliOrder.PrezzoTotProdotti(orderDTO);

        Orders ordine = new Orders();
        ordine.setStatus(STATOORDINE.PENDING.getStatoOrdine());
        ordine.setTotal(orderDTO.getTotal());

        //mappare i prodotti dal tipo ProductInfoDTO al tipo Order_items
        List<? extends BaseEntity> listaProd = mapper.MapperToListType(orderDTO.getListaProdotti(), Order_Items.class);

        List<Order_Items> listaProdottiOrderItems = listaProd.stream().map
                (item ->
                        (Order_Items) item).toList();


        ordine.setOrderItemsList(listaProdottiOrderItems);
        ordine.setUser(user);
        ordine.setCreated_at(currTime);

        for (Order_Items item : listaProdottiOrderItems) {
            item.setOrder(ordine);
        }

        orderRepository.save(ordine);

        // return mapper.mapperOrderDTO(ordine.getId(), user.getName(), ordine.getStatus(), ordine.getTotal(), currentTime);
        return mapper.toDTO(ordine, new StandardOrderDTO());
    }

    public String StatusProcessing(Long id) {

        Orders ordine = FindOrder(id, orderRepository);

        if (ordine.getStatus().equals(STATOORDINE.PROCESSING.getStatoOrdine())) {
            return "L'ordine è gia impostato su processing.";
        }

        ordine.setStatus(STATOORDINE.PROCESSING.getStatoOrdine());
        orderRepository.save(ordine);
        return "ordine correttamente impostato su processing";
    }

    public String StatusShipped(Long id) {
        Orders ordine = FindOrder(id, orderRepository);

        if (ordine.getStatus().equals(STATOORDINE.SHIPPED.getStatoOrdine())) {
            return "L'ordine è gia impostato su shipped.";
        }

        ordine.setStatus(STATOORDINE.SHIPPED.getStatoOrdine());
        orderRepository.save(ordine);
        return "ordine correttamente impostato su shipped";
    }

    public String StatusCompleted(Long id) {
        Orders ordine = FindOrder(id, orderRepository);

        if (ordine.getStatus().equals(STATOORDINE.COMPLETED.getStatoOrdine())) {
            return "L'ordine è gia impostato su completed.";
        }

        ordine.setStatus(STATOORDINE.COMPLETED.getStatoOrdine());
        orderRepository.save(ordine);
        return "ordine correttamente impostato su completed";
    }


    // metodo comune per la ricerca dell'ordine
    private Orders FindOrder(Long id, OrderRepository orderRepository) {

        Optional<Orders> optOrder = orderRepository.findById(id);

        if (optOrder.isEmpty()) {
            throw new RuntimeException("non esiste un ordine per l'id selezionato.");
        }


        return optOrder.get();
    }


    public BaseDTO ModificaOrdine(EditOrdineDTO dataOrdine) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        // controllo esistenze dell ordine
        Optional<Orders> optionalOrder = orderRepository.findById(dataOrdine.getOrderId());

        if (optionalOrder.isEmpty()) {
            throw new RuntimeException("l'ordine selezionato non esiste");
        }

        Orders ordine = optionalOrder.get();

        if (ordine.getStatus().equals(STATOORDINE.SHIPPED.getStatoOrdine())
                || ordine.getStatus().equals(STATOORDINE.COMPLETED.getStatoOrdine())) {
            throw new RuntimeException("l'ordine è stato già spedito o completato. Impossibile modificare.");

        }

        // controllo che utente che effettua ordine sia stesso del possessore dell ordine su db
        if (!ordine.getUser().getId().equals(Long.parseLong(dataOrdine.getUserId()))) {
            throw new RuntimeException("utente possessore dell'ordine su db" +
                    " ed utente che ha inviato la richiesta di modifica ordine non sono la stessa persona.");
        }

        //controllo che i totali tra prodotti e totale corrispondano
        checkCalcoliOrder.PrezzoTotProdotti(dataOrdine);

        ordine.getOrderItemsList().clear();
        List<? extends BaseEntity> nuovaListaOrdini = mapper.MapperToListType(dataOrdine.getListaProdotti(), Order_Items.class);

        List<Order_Items> listaProdottiOrderItems = nuovaListaOrdini.stream().map
                (item ->
                        (Order_Items) item).toList();

        ordine.getOrderItemsList().addAll(listaProdottiOrderItems);

        for (Order_Items item : listaProdottiOrderItems) {
            item.setOrder(ordine);
        }
        ordine.setTotal(dataOrdine.getTotal());
        orderRepository.save(ordine);

        //  Time currentTime = new Time(System.currentTimeMillis());
        //return mapper.mapperOrderDTO(ordine.getId(), ordine.getUser().getName(), ordine.getStatus(), ordine.getTotal(), currentTime);
        return mapper.toDTO(ordine, new StandardOrderDTO());
    }


    public BasedDTO_GET GetSingoloOrdine(Long idOrdine) {

        Optional<Orders> OptOrder = this.orderRepository.findById(idOrdine);

        if (OptOrder.isEmpty()) {
            throw new RuntimeException("ordine selezionato non esiste.");
        }

        Orders order = OptOrder.get();
        return this.mapper.GeneralMethodToCastEntityToGetDTO(order);

    }

    public List<BasedDTO_GET> GetAllOrdiniUtente(Long idUser) {
        Optional<Users> utenteOpt = userRepository.findById(idUser);

        if (utenteOpt.isEmpty()) {
            throw new RuntimeException("utente selezionato non esiste.");
        }

        Users utente = utenteOpt.get();

        List<Optional<Orders>> listaOrdersOpt = this.orderRepository.findOrdersByUser((utente));

        if (listaOrdersOpt.isEmpty()) {
            throw new RuntimeException("nessun ordine da mostrare.");
        }


        // se il singolo elemento all interno dell'optional esiste,
        // allora lo pusho dentro una lista di entity Carts.
        List<Orders> listaOrdiniUtente = new ArrayList<>();

        List<BasedDTO_GET> listaOrdiniDTO = new ArrayList<>();

        // aggiungo l 'elemento all interno della lista di Carts
        for (Optional<Orders> carrItem : listaOrdersOpt) {

            if (carrItem.isPresent()) {
                listaOrdiniUtente.add(carrItem.get());
            }

        }

        // ciclo la lista di Carts e passo ogni oggetto ad un mapper che ritorna un oggetto CartGet_DTO
        // che pusho in listaCarrelloDTO
        // che poi ritorno al controller
        for (Orders order : listaOrdiniUtente) {

            BasedDTO_GET cartDTO = this.mapper.GeneralMethodToCastEntityToGetDTO(order);
            listaOrdiniDTO.add(cartDTO);
        }

        return listaOrdiniDTO;
    }
}
