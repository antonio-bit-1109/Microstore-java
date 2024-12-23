package org.example.microstoreprogetto.util.configuration;

import org.example.microstoreprogetto.util.filter.JWTAuthFilter;
import org.example.microstoreprogetto.util.generateJWTtoken.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // file di configurazione di spring --
// qui definisco oggetti Bean di configurazione che inietterò nelle varie classi
@EnableWebSecurity  // specifico a spring che non voglio le impostazioni di default ma personalizzate
public class SecurityConfiguration {

    private final JwtUtil jwtUtil;

    public SecurityConfiguration(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/users/register").permitAll()
                        .requestMatchers("/order/create").hasRole("ADMIN") // questo dovrebbe essere il valore salvato nel token, controlla anche se maiuscolo o minuscolo
                        .anyRequest().authenticated())
                .addFilterBefore(new JWTAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disabilita CSRF per API RESTful
//                .authorizeHttpRequests(auth -> auth
//
//                                .requestMatchers("/auth/login", "/users/register").permitAll()
//                                .requestMatchers("/order/create").hasRole("ADMIN")
//                                .anyRequest().authenticated()// Endpoint di autenticazione pubblici
//
//
//                        //    .anyRequest().permitAll()
//                )
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// Stateless per JWT
//        .addFilterBefore(new JWTAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); // Aggiungi filtro JWT
//
//        return http.build();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
