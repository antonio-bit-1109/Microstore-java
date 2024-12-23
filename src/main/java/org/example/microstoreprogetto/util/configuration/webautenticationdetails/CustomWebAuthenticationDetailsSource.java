package org.example.microstoreprogetto.util.configuration.webautenticationdetails;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class CustomWebAuthenticationDetailsSource extends WebAuthenticationDetailsSource {


    public WebAuthenticationDetails buildDetails(HttpServletRequest context, String iduser) {
        return new CustomWebAuthenticationDetails(context, iduser);
    }
}
