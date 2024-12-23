package org.example.microstoreprogetto.PRODUCTS.service;

import org.example.microstoreprogetto.PRODUCTS.DTO.AddStockProductDTO;
import org.example.microstoreprogetto.PRODUCTS.DTO.CreateProductDTO;
import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.PRODUCTS.repository.ProductRepository;
import org.example.microstoreprogetto.util.configuration.Mapper;
import org.example.microstoreprogetto.util.enums.categoryproduct.CategoryProduct;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

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

            //se esiste gia un prodotto con caratteristiche simili evito la creazione
            if (productRepository.findByName(prodotto.normalizedName()) != 0) {
                throw new RuntimeException("questo prodotto esiste già. Se vuoi puoi modificare le quantità.");
            }


            prod.setName(prodotto.normalizedName());
            prod.setDescription(prodotto.getDescription());
            prod.setCategory(CategoryProduct.GENERAL.getDescrizione());
            prod.setStock(1);
            prod.setPrice(prodotto.getPrice());
            prod.setCreated_at(currentTime);
            prod.setIs_active(true);

            productRepository.save(prod);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public StandardProductDTO addStockQuantity(AddStockProductDTO prodStockDTO) {

        Optional<Products> optionalProduct = productRepository.findById(prodStockDTO.getId());

        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("il prodotto non esiste.");
        }

        Products prodottoTrovato = optionalProduct.get();
        prodottoTrovato.setStock(prodottoTrovato.getStock() + prodStockDTO.getStock());
        productRepository.save(prodottoTrovato);

        return this.mapper.MapperProductDto(
                prodottoTrovato.getName(),
                prodottoTrovato.getCategory(),
                Float.toString(prodottoTrovato.getPrice()),
                prodottoTrovato.getDescription(),
                Integer.toString(prodottoTrovato.getStock()),
                prodottoTrovato.getIs_active()
        );

        // return mapper.getProductDTO();
    }

    public StandardProductDTO getProduct(Long id) {

        Optional<Products> optionalProducts = productRepository.findById(id);

        if (optionalProducts.isEmpty()) {
            throw new RuntimeException("il prodotto non esiste.");
        }

        Products prodottoTrovato = optionalProducts.get();

        return this.mapper.MapperProductDto(
                prodottoTrovato.getName(),
                prodottoTrovato.getCategory(),
                Float.toString(prodottoTrovato.getPrice()),
                prodottoTrovato.getDescription(),
                Integer.toString(prodottoTrovato.getStock()),
                prodottoTrovato.getIs_active()
        );

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
}
