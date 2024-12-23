package org.example.microstoreprogetto.ADMIN.service;

import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;
import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.example.microstoreprogetto.PRODUCTS.repository.ProductRepository;
import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final ProductRepository productRepository;

    public AdminService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


}


//public List<StandardUserDTO> prendiTuttiUtenti() {
//
//    List<StandardUserDTO> listaUtentiDTO = new ArrayList<>();
//    List<Users> listaUtenti = userRepository.findAll();
//
//    for (Users utente : listaUtenti) {
//        listaUtentiDTO.add(mapper.mapperUserDTO(utente.getName(), utente.getEmail(), utente.getPhone(), utente.getIsActive()));
//    }
//    return listaUtentiDTO;
//}