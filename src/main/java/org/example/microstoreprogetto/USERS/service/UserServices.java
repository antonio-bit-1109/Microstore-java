package org.example.microstoreprogetto.USERS.service;

import org.example.microstoreprogetto.USERS.DTO.CreateUserDTO;
import org.example.microstoreprogetto.USERS.DTO.EditUserDTO;
import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.USERS.repository.UserRepository;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.configuration.Mapper;
import org.example.microstoreprogetto.util.generateJWTtoken.JwtUtil;
import org.example.microstoreprogetto.util.enums.roles.ROLES;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // private final JwtUtil jwtUtil;
    private final Mapper mapper;

    //costrutt
    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder, Mapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        // this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    // get di tutti gli utenti -- solo admin puo farlo
    public List<BaseDTO> prendiTuttiUtenti() {

        List<BaseDTO> listaUtentiDTO = new ArrayList<>();
        List<Users> listaUtenti = userRepository.findAll();

        for (Users utente : listaUtenti) {
            // listaUtentiDTO.add(mapper.mapperUserDTO(utente.getName(), utente.getEmail(), utente.getPhone(), utente.getIsActive()));
            listaUtentiDTO.add(mapper.toDTO(utente, new StandardUserDTO()));
        }
        return listaUtentiDTO;
    }

    public Optional<Users> findUserbyId(Long id) {
        return userRepository.findById(id);
    }


    // prima di salvare l utente nel db faccio hashing della password tramite BCrypt

    public BaseDTO RegistrationSave(CreateUserDTO userData) {

        Users userDTO = new Users();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        userDTO.setName(userData.getName());
        userDTO.setEmail(userData.getEmail());
        userDTO.setPassword(this.passwordEncoder.encode(userData.getPassword()));
        userDTO.setRole(ROLES.USER.getRole());

        if (userData.getPhone() != null && !userData.getPhone().isEmpty()) {
            userDTO.setPhone(userData.getPhone());
        }

        userDTO.setCreated_at(currentTime);
        userDTO.setIsActive(true);

        Users user = userRepository.save(userDTO);

        return mapper.toDTO(user, new StandardUserDTO());

        //  return this.mapper.mapperUserDTO(user.getName(), user.getEmail(), user.getPhone(), user.getIsActive());
    }

//    public BaseDTO EditSave(Users user) {
//        Users userEntity = userRepository.save(user);
//        return mapper.toDTO(userEntity, new StandardUserDTO());
//        // return mapper.mapperUserDTO(userEntity.getName(), userEntity.getEmail(), userEntity.getPhone(), userEntity.getIsActive());
//    }

    public void softDelete(Optional<Users> OptionalUser) {

        if (OptionalUser.isPresent()) {
            Users user = OptionalUser.get();

            if (user.getIsActive().equals(false)) {
                throw new RuntimeException("L'utente risulta già deattivato.");
            }

            userRepository.softDelete(user);
        } else {
            throw new RuntimeException("utente selezionato non esiste.");
        }

    }

    public int Reactivate(Optional<Users> user) {

        // nessun utente trovato
        if (user.isEmpty()) {
            return 0;
        }

        Users myUser = user.get();
        // utente gia attivo
        if (myUser.getIsActive().equals(true)) {
            return 1;
        }

        // riattivazione account
        userRepository.reactivateAccount(myUser);
        return 2;

    }

    private void FindByEmail_alreadyExist(Users user) {
        int res = userRepository.existsByEmail(user.getEmail());

        if (res == 1) {
            throw new RuntimeException(" questa mail è gia presente. Impossibile utilizzare. Cambia email.");
        }
    }


    public BaseDTO CheckAndMapUser(Optional<Users> optionalUser, EditUserDTO editData) {
        try {

            if (optionalUser.isPresent()) {

                Users user = optionalUser.get();

                if (editData.getName() != null && !editData.getName().isEmpty()) {
                    user.setName(editData.getName());
                }
                if (editData.getEmail() != null && !editData.getEmail().isEmpty()) {
                    user.setEmail(editData.getEmail());
                }
                if (editData.getPassword() != null && !editData.getPassword().isEmpty()) {
                    user.setPassword(editData.getPassword());
                }

                if (editData.getPhone() != null && !editData.getPhone().isEmpty()) {
                    user.setPhone(editData.getPhone());
                }
                // controllo che non esista gia un utente con questa email
                FindByEmail_alreadyExist(user);
                userRepository.save(user);
                return mapper.toDTO(user, new StandardUserDTO());
            } else {

                throw new RuntimeException("l'utente selezionato non esiste.");
            }


        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }


    }

    public BaseDTO TrovaUtente(Long id) {

        Optional<Users> utenteOpt = userRepository.findById(id);

        if (utenteOpt.isEmpty()) {
            throw new RuntimeException("utente selezionato non esiste.");
        }

        Users user = utenteOpt.get();
        return mapper.toDTO(user, new StandardUserDTO());
        //  return mapper.mapperUserDTO(user.getName(), user.getEmail(), user.getPhone(), user.getIsActive());
    }
}
