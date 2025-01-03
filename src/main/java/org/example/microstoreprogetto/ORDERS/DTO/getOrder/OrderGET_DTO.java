package org.example.microstoreprogetto.ORDERS.DTO.getOrder;

import org.example.microstoreprogetto.CARTS.DTO.getCart.CartItemsDTO;
import org.example.microstoreprogetto.util.base_dto.BasedDTO_GET;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderGET_DTO extends BasedDTO_GET {

    private Long idOrdine;
    private String status;
    private String owner;
    private List<OrderItemsDTO> listaProdotti;
    private String total;

    public void setIdOrdine(Long idOrdine) {
        this.idOrdine = idOrdine;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setListaProdotti(List<OrderItemsDTO> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    public void AddToListaProdotti(OrderItemsDTO item) {
        getListaProdotti().add(item);
    }

    public void setStatus(String createdAt) {
        this.status = createdAt;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    //costrutt
    public OrderGET_DTO() {
        setListaProdotti(new ArrayList<>());
    }

    public String getTotal() {
        return total;
    }

    public Long getIdOrdine() {
        return idOrdine;
    }

    public List<OrderItemsDTO> getListaProdotti() {
        return listaProdotti;
    }

    public String getStatus() {
        return status;
    }

    public String getOwner() {
        return owner;
    }
}
