package org.example.microstoreprogetto.ORDERS.service;

import org.example.microstoreprogetto.ORDERS.DTO.CreateOrderDTO;
import org.example.microstoreprogetto.ORDERS.DTO.EditOrdineDTO;
import org.example.microstoreprogetto.ORDERS.DTO.StandardOrderDTO;
import org.example.microstoreprogetto.ORDERS.entity.Orders;
import org.example.microstoreprogetto.ORDERS.repository.OrderRepository;
import org.example.microstoreprogetto.ORDERS.entity.Order_Items;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.USERS.repository.UserRepository;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.configuration.CheckCalcoliOrder;
import org.example.microstoreprogetto.util.configuration.Mapper;
import org.example.microstoreprogetto.util.enums.statoordine.STATOORDINE;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Time;
import java.sql.Timestamp;
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

    public BaseDTO creazioneOrdine(CreateOrderDTO orderDTO) {

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
        List<Order_Items> listaProd = mapper.MapperToOrderListType(orderDTO.getListaProdotti());


        ordine.setOrderItemsList(listaProd);
        ordine.setUser(user);
        ordine.setCreated_at(currTime);

        for (Order_Items item : listaProd) {
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


    public StandardOrderDTO ModificaOrdine(EditOrdineDTO dataOrdine) {

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
        List<Order_Items> nuovaListaOrdini = mapper.MapperToOrderListType(dataOrdine.getListaProdotti());

        ordine.getOrderItemsList().addAll(nuovaListaOrdini);

        for (Order_Items item : nuovaListaOrdini) {
            item.setOrder(ordine);
        }
        ordine.setTotal(dataOrdine.getTotal());
        orderRepository.save(ordine);

        Time currentTime = new Time(System.currentTimeMillis());
        return mapper.mapperOrderDTO(ordine.getId(), ordine.getUser().getName(), ordine.getStatus(), ordine.getTotal(), currentTime);
    }


}
