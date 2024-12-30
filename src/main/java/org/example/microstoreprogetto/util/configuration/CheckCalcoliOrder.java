package org.example.microstoreprogetto.util.configuration;

import org.example.microstoreprogetto.ORDERS.DTO.CreateOrderDTO;
import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.PRODUCTS.util.UtilityProduct;
import org.springframework.stereotype.Component;


@Component
public class CheckCalcoliOrder {

    private Double sommaTot;
    private final UtilityProduct utilityProduct;

    public void setSommaTot(Double sommaTot) {
        this.sommaTot = (sommaTot);
    }

    //costrutt
    public CheckCalcoliOrder(UtilityProduct utilityProduct) {
        setSommaTot(0.0);
        this.utilityProduct = utilityProduct;
    }


    public Double getSommaTot() {
        return sommaTot;
    }

    public static double truncateDouble(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.floor(value * factor) / factor;
    }

    public void PrezzoTotProdotti(CreateOrderDTO OrdineData) {

        // controllo che lo userid inviato dal client, il quale è possessore dell ordine,
        // combaci con lo user id che possiede l ordine nel db
        utilityProduct.CheckIfOwnerOrderFromClientSameAsOwnerDb(OrdineData.getUserId());

        // passo una lista nel primo parametro ed il tipo della lista nel secondo parametro
        // controlla esistenza dei prodotti che vengono inviati dal client
        // (controllo che id prodotto inviato dal client esista nel db)
        utilityProduct.CheckProductExistence(OrdineData.getListaProdotti(), ProductInfoDTO.class);


        //controllo che il prezzo del prodotto inviato dal client sia lo stesso prezzo del prodotto salvato su db
        utilityProduct.CheckPricesCorrispondation(OrdineData.getListaProdotti());


        // check somme prezzi dei prodotti sia uguale al totale specificato nel json
        setSommaTot(0.0);

        for (ProductInfoDTO prodotto : OrdineData.getListaProdotti()) {
            setSommaTot(getSommaTot() + (prodotto.getQuantity() * prodotto.getPrezzoUnitario()));
        }

        if (!(truncateDouble(getSommaTot(), 2) == (OrdineData.getTotal())) &&
                !(truncateDouble(getSommaTot(), 2) == (OrdineData.getTotal() + 00.01)) &&
                !(truncateDouble(getSommaTot(), 2) == (OrdineData.getTotal() - 00.01))) {
            throw new RuntimeException("errore. Mismatch tra prezzo totale dell'ordine e valore atteso.");
        }

        setSommaTot(0.0);

        // controllo che la quantità selezionata di prodotto, inviata dal client,
        // sia >= alla quantità di prodotto presente nel db
        utilityProduct.CheckStockQuantityAndSubtract(OrdineData.getListaProdotti());
    }

}
