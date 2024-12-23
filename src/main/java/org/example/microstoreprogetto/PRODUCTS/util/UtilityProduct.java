package org.example.microstoreprogetto.PRODUCTS.util;

import org.example.microstoreprogetto.ORDERS.DTO.CreateOrderDTO;
import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.PRODUCTS.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class UtilityProduct {

    private final ProductRepository productRepository;
    private List<Long> listaIdProdotti;
    private List<Products> listaProdottiTrovatiDb;
    Map<Long, Float> mappaProdotti;

    public void setMappaProdotti(Map<Long, Float> mappaProdotti) {
        this.mappaProdotti = mappaProdotti;
    }

    public void setListaProdottiTrovatiDb(List<Products> listaProdottiTrovatiDb) {
        this.listaProdottiTrovatiDb = listaProdottiTrovatiDb;
    }

    public void setListaIdProdotti(List<Long> listaIdProdotti) {
        this.listaIdProdotti = listaIdProdotti;
    }

    //costrutt
    public UtilityProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
        setListaIdProdotti(new ArrayList<>());
    }

    public Map<Long, Float> getMappaProdotti() {
        return mappaProdotti;
    }

    public void AddToMappaProdotti(Long id, Float price) {
        getMappaProdotti().put(id, price);
    }

    public List<Products> getListaProdottiTrovatiDb() {
        return listaProdottiTrovatiDb;
    }

    public List<Long> getListaIdProdotti() {
        return listaIdProdotti;
    }

    public int getSizeLista() {
        return getListaIdProdotti().size();
    }

    private void ResetSizeLista() {
        getListaIdProdotti().clear();
    }


    private void AddToListaIdProdotti(Object idProd) {
        try {
            Long idProdotto = Long.parseLong((String) idProd);
            getListaIdProdotti().add(idProdotto);

        } catch (NumberFormatException e) {

            throw new NumberFormatException("id prodotto non castabile a Long o a stringa");
        }
    }

    private void checkProduct() throws IllegalAccessException {

        try {

            for (Long idProdotto : getListaIdProdotti()) {
                Optional<Products> possibileProdotto = productRepository.findById(idProdotto);

                if (possibileProdotto.isEmpty()) {
                    throw new IllegalAccessException(("il prodotto con id " + idProdotto + " non esiste"));
                }

            }


            // trovo tutte le entità e le inserisco in una lista.
            List<Products> foundedProducts = productRepository.findAllById(getListaIdProdotti());

            // se i controlli vanno tutti i buon fine salvo la lista dei prodotti tornata dal db per un successivo controllo
            setListaProdottiTrovatiDb(foundedProducts);
            ResetSizeLista();

        } catch (IllegalArgumentException ex) {
            throw new IllegalAccessException("Uno dei prodotti nella lista non esiste: " + ex.getMessage());
        }


        // mi aspetto che la lista foundedProducts abbia la stessa lunghezza della lista dei prodotti arrivata da client
        // se le due dimensioni differiscono c'è qualche problema

        // mi aspetto che le due liste abbiano la stessa dimensione
        // in quanto sto cercando sul db i prodotti che mi sono arrivati dal client
//        if (getSizeLista() != foundedProducts.size()) {
//            throw new RuntimeException("il size della lista passata da client e di quella ritrovata sul db non combaciano.");
//        }

    }

    public <T> void CheckProductExistence(List<T> listaProdotti, Class<T> tClass) {
        try {

            for (T prod : listaProdotti) {

                if (tClass.isInstance(prod)) {

                    T castedProd = tClass.cast(prod);
                    // Puoi usare la riflessione per interagire con i metodi/proprietà dell'oggetto
                    // Esempio generico: verifica se esiste un metodo `getIdProd`


                    var method = tClass.getMethod("getIdProd");
                    Object idProd = method.invoke(castedProd);

                    if (idProd != null) {
                        // Se trovi un prodotto con id non nullo, aggiungilo alla lista
                        AddToListaIdProdotti(idProd);
                    } else {
                        throw new RuntimeException("uno degli id prodotto non è stato trovato.");
                    }

                }

            }
            // una volta acquisiti tutti gli id prodotti faccio una query al product repository
            // per controllare che esistano tutti quanti.
            checkProduct();

        } catch (NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            // Se il metodo non esiste o ci sono errori di accesso, loggalo
            throw new RuntimeException("Errore durante l'accesso al metodo: " + e.getMessage());
        }
    }

    // avendo la lista dei prodotti ritornata dal db, faccio un ulteriore controllo per vedere se i prezzi dei prodotti
    // in arrivo dal client, corrispondono ai prezzi effettivi dei prodotti sul db.
    public void CheckPricesCorrispondation(List<ProductInfoDTO> listaProdottiClient) {

        // soluzione con time complexity O(n) -- velocità computazionale costante
//        Map<Long, Float> mappaProdotti = new HashMap<>();
        setMappaProdotti(new HashMap<>());

        for (Products p : getListaProdottiTrovatiDb()) {
            AddToMappaProdotti(p.getId(), p.getPrice());
            // mappaProdotti.put((p.getId()), p.getPrice());
        }


        for (ProductInfoDTO prod : listaProdottiClient) {
            Long prodId = Long.parseLong(prod.getIdProd());
            if (getMappaProdotti().containsKey(prodId)) {
                Float prezzoProdotto = getMappaProdotti().get(prodId);
                Double prezzoProdottoDouble = BigDecimal.valueOf(prezzoProdotto)
                        .setScale(2, RoundingMode.DOWN)
                        .doubleValue();

                if (!prezzoProdottoDouble.equals(prod.getPrezzoUnitario()) &&
                        !prezzoProdottoDouble.equals(prod.getPrezzoUnitario() + 00.01) &&
                        !prezzoProdottoDouble.equals(prod.getPrezzoUnitario() - 00.01)) {

                    throw new RuntimeException("il prezzo del prodotto arrivato dal client è diverso dal prezzo del prodotto salvato sul DB:" +
                            "IDPRODOTTO: " + prodId + " PREZZO ORIGINALE: " + prezzoProdotto + " PREZZO TROVATO NEL JSON: " + prod.getPrezzoUnitario());
                }
            }
        }

    }

    public void CheckStockQuantityAndSubtract(List<ProductInfoDTO> listaProdottiClient) {


        for (ProductInfoDTO prodotto : listaProdottiClient) {
            Long prodId = Long.parseLong(prodotto.getIdProd());

            for (Products prodottoDb : getListaProdottiTrovatiDb()) {

                if (prodottoDb.getId().equals(prodId)) {

                    if (prodotto.getQuantity() > prodottoDb.getStock()) {
                        throw new RuntimeException("la quantità di prodotto richiesta è superiore alle scorte di magazzino." +
                                "ID Prodotto: " + prodottoDb.getId() + " Nome prodotto " + prodottoDb.getName() +
                                " Quantità presente in magazzino : " + prodottoDb.getStock() + " Quantità richieste: " + prodotto.getQuantity());
                    }
                    //rimuovi quantità stock prodotto e salva
                    prodottoDb.setStock(prodottoDb.getStock() - prodotto.getQuantity());
                    productRepository.save(prodottoDb);
                }
            }

        }


        // alla fine dei controlli resetto la lista dei prodotti ricavati dal db,
        // a partire dagli id prodotto arrivati dal client
        getListaProdottiTrovatiDb().clear();
    }


    public void CheckIfOwnerOrderFromClientSameAsOwnerDb(String utenteFacenteOrdine) {

    }
}
