package org.mpo.newstracker.util;

import org.mpo.newstracker.exception.BackendException;
import org.mpo.newstracker.exception.TranslatorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@Component
public class GoogleTranslator {
    @Value("${google-translator.script-url}")
    private String scriptUrl;

    public GoogleTranslator() {
    }

    public String translate(String langFrom, String langTo, String text) throws TranslatorException, BackendException {

            //System.out.println("lengtrh text to translate: " + text.length());

        try {

            String urlStr = scriptUrl+
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;
            URL url = new URL(urlStr);

            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("UserDao-Agent", "Mozilla/5.0");


            if (con.getResponseCode() < 400) {
                try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);

                    }

                    String responseString = response.toString().replaceAll("&#39;", "'");
                    return responseString;
                }
            } else {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }


                }
                //System.out.println("TRANSLATOR EXCEPTION: "+ response);
                //return response.toString();
                Throwable t = new Throwable("TranslatorException");

                throw new TranslatorException(GoogleTranslator.class.getSimpleName()+" : "+response.toString(),t.fillInStackTrace(),400);
            }
        }catch(IOException e){
            throw new BackendException(e.getClass().getSimpleName()+" : "+ e.getMessage(),e.getCause(),400);
        }

    }

}
