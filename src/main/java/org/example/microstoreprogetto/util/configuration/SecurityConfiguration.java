package org.example.microstoreprogetto.util.configuration;

import org.example.microstoreprogetto.util.filter.JWTAuthFilter;
import org.example.microstoreprogetto.util.generateJWTtoken.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration // file di configurazione di spring --
// qui definisco oggetti Bean di configurazione che inietterò nelle varie classi
@EnableWebSecurity  // specifico a spring che non voglio le impostazioni di default ma personalizzate
public class SecurityConfiguration {

    private final JwtUtil jwtUtil;

    public SecurityConfiguration(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//
//                        .requestMatchers("/auth/login", "/users/register").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // questo dovrebbe essere il valore salvato nel token, controlla anche se maiuscolo o minuscolo
//                        .anyRequest().authenticated())
//                //   .anyRequest().permitAll())
//                .addFilterBefore(new JWTAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:4202")); // Cambia con l'URL del tuo frontend
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/users/register").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
//                        .anyRequest().permitAll())
                .addFilterBefore(new JWTAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    // oggetto utilizzabile nella mia applicazione Spring per fare l hash della password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // oggetto per configurare le opzioni cors per accettare richieste da specifici client
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:5173")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
}
