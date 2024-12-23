package org.example.microstoreprogetto.util.extractfromcontextholderspringsecurity;

import org.springframework.stereotype.Component;

@Component
public class ExtractDataFromSecurityContextHolder {


    //    CustomWebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null, UserId=22]
    // la stringa è formattata cosi
    public String ExtractIdUser(String contextData) {


        if (!contextData.contains("UserId")) {
            throw new RuntimeException("il valore id utente, che dovrebbe essere presente nel token, è invece null.");

        }

        int startSequence = contextData.indexOf("UserId=");

        if (startSequence == -1) {
            throw new IllegalArgumentException("Start sequence not found in the input string.");
        }

        return contextData.substring(startSequence + 7, contextData.length() - 1);
        
    }
}
