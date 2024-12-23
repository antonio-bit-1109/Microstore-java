package org.example.microstoreprogetto.util.configuration.webautenticationdetails;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private String idUser;

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public CustomWebAuthenticationDetails(HttpServletRequest request, String iduser) {
        super(request);
        setIdUser(iduser);
    }

    public CustomWebAuthenticationDetails(String remoteAddress, String sessionId) {
        super(remoteAddress, sessionId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(" [");
        sb.append("RemoteIpAddress=").append(this.getRemoteAddress()).append(", ");
        sb.append("SessionId=").append(this.getSessionId()).append(", ");
        sb.append("UserId=").append(this.getIdUser()).append("]");
        return sb.toString();
    }
}
