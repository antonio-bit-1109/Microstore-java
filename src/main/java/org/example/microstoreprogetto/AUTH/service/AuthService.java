package org.example.microstoreprogetto.AUTH.service;

import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.USERS.repository.UserRepository;
import org.example.microstoreprogetto.util.generateJWTtoken.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String autenticate(String email, String password) {
        Optional<Users> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new RuntimeException("utente non trovato. Email o password errate.");
        }

        Users presentUser = user.get();

        if (this.passwordEncoder.matches(password.trim(), presentUser.normalizedPassword())) {
            // generiamo il token jwt da reinviare al client
            return JwtUtil.generateToken(presentUser);

        } else {
            throw new RuntimeException("Nome utente o password errata.");
        }
    }
}
