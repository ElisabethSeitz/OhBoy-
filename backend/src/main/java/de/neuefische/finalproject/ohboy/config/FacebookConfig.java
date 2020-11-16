package de.neuefische.finalproject.ohboy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacebookConfig {
    @Value("${oauth.facebook.client.id}")
    private String clientId;
    @Value("${oauth.facebook.client.secret}")
    private String clientSecret;
    @Value("${oauth.facebook.redirect.uri}")
    private String redirectUri;


    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}

