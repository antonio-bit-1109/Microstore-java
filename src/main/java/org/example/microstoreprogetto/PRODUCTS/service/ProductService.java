package org.example.microstoreprogetto.PRODUCTS.service;

import org.example.microstoreprogetto.PRODUCTS.DTO.AddStockProductDTO;
import org.example.microstoreprogetto.PRODUCTS.DTO.CreateProductDTO;
import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.PRODUCTS.repository.ProductRepository;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.configuration.mapperutils.Mapper;
import org.example.microstoreprogetto.util.enums.categoryproduct.CategoryProduct;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final Mapper mapper;

    public ProductService(ProductRepository productRepository, Mapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    public void productCreation(CreateProductDTO prodotto) {

        try {

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            Products prod = new Products();
            Random random = new Random();
            //se esiste gia un prodotto con caratteristiche simili evito la creazione
            if (productRepository.findByName(prodotto.normalizedName()) != 0) {
                throw new RuntimeException("questo prodotto esiste già. Se vuoi puoi modificare le quantità.");
            }

            int n = random.nextInt(3);

            prod.setName(prodotto.normalizedName());
            prod.setDescription(prodotto.getDescription());
            switch (n) {
                case 1:
                    prod.setCategory(CategoryProduct.FOOD.getDescrizione());
                    break;
                case 2:
                    prod.setCategory(CategoryProduct.WEAPON.getDescrizione());
                    break;
                default:
                    prod.setCategory(CategoryProduct.GENERAL.getDescrizione());
                    break;
            }
//            prod.setCategory(CategoryProduct.GENERAL.getDescrizione());
            prod.setStock(1);
            prod.setPrice(prodotto.getPrice());
            prod.setCreated_at(currentTime);
            prod.setIs_active(true);
            prod.setImage_url(prodotto.getImage_url());

            productRepository.save(prod);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public BaseDTO addStockQuantity(AddStockProductDTO prodStockDTO) {

        Optional<Products> optionalProduct = productRepository.findById(prodStockDTO.getId());

        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("il prodotto non esiste.");
        }

        Products prodottoTrovato = optionalProduct.get();
        prodottoTrovato.setStock(prodottoTrovato.getStock() + prodStockDTO.getStock());
        productRepository.save(prodottoTrovato);

//        return this.mapper.MapperProductDto(
//                prodottoTrovato.getName(),
//                prodottoTrovato.getCategory(),
//                Float.toString(prodottoTrovato.getPrice()),
//                prodottoTrovato.getDescription(),
//                Integer.toString(prodottoTrovato.getStock()),
//                prodottoTrovato.getIs_active()
//        );
        return mapper.toDTO(prodottoTrovato, new StandardProductDTO());

    }

    public BaseDTO getProduct(Long id) {

        Optional<Products> optionalProducts = productRepository.findById(id);

        if (optionalProducts.isEmpty()) {
            throw new RuntimeException("il prodotto non esiste.");
        }

        Products prodottoTrovato = optionalProducts.get();

//        return this.mapper.MapperProductDto(
//                prodottoTrovato.getName(),
//                prodottoTrovato.getCategory(),
//                Float.toString(prodottoTrovato.getPrice()),
//                prodottoTrovato.getDescription(),
//                Integer.toString(prodottoTrovato.getStock()),
//                prodottoTrovato.getIs_active()
//        );

//        StandardProductDTO stanProdDTO = new StandardProductDTO();
        return this.mapper.toDTO(prodottoTrovato, new StandardProductDTO());

        // return mapper.getProductDTO();
    }

    public void deleteProduct(Long id) {
        Optional<Products> optionalProducts = productRepository.findById(id);

        if (optionalProducts.isEmpty()) {
            throw new RuntimeException("il prodotto non esiste.");
        }

        Products prodottoTrovato = optionalProducts.get();
        prodottoTrovato.setIs_active(false);
        productRepository.save(prodottoTrovato);
    }

    public List<BaseDTO> GetTuttiProdotti() {

        List<Products> listaProdottiDB = productRepository.findAll();

        List<BaseDTO> listaProdDTO = new ArrayList<>();

        for (Products prodotto : listaProdottiDB) {
            listaProdDTO.add(mapper.toDTO(prodotto, new StandardProductDTO()));
        }

        return listaProdDTO;

    }

    public void reimpostaActive(Long id) {

        Optional<Products> prodottoOptional = this.productRepository.findById(id);

        if (prodottoOptional.isEmpty()) {
            throw new RuntimeException("impossibile trovare prodotto con id specificato.");
        }

        Products prodotto = prodottoOptional.get();

        if (prodotto.getIs_active()) {
            throw new RuntimeException(("il prodotto è già impostato come visualizzabile"));
        }

        prodotto.setIs_active(true);
        productRepository.save(prodotto);
    }
}
