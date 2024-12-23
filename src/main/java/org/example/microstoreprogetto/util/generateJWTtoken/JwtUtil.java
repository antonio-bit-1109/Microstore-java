package org.example.microstoreprogetto.util.generateJWTtoken;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 giorno in millisecondi

    // metodo da richiamare una volta che un utente è confermato essere presente nel db,
    // genero token e lo reinvio al client
    public static String generateToken(Users users) {
        return Jwts.builder()
                .setSubject(users.getName())
                .claim("role", users.getRole().toUpperCase())
                .setId((users.getId().toString()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String ExtractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String getRoleFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role", String.class);

        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String getIdFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getId();

        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

//    public Map<String, String> validateToken(String token) {
//        try {
//
//            var claims = Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
////                    .getSubject();
//            //            String role = claims.get("role", String.class);
////
//            HashMap<String, String> resultClaim = new HashMap<>();
//            resultClaim.put("username", claims.getSubject());
//            // resultClaim.put("role", role);
//            //  return claims.getSubject();
//            return resultClaim;
//
//        } catch (JwtException | IllegalArgumentException e) {
//            return null;
//        }
//    }
}
