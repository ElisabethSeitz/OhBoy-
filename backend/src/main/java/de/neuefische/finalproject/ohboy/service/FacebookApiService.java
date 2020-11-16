package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.config.FacebookConfig;
import de.neuefische.finalproject.ohboy.dto.FacebookGetAccessTokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class FacebookApiService {

    private final static String  FACEBOOK_API_ACCESS_TOKEN_URL = "https://graph.facebook.com/v9.0/oauth/access_token";

    private final FacebookConfig facebookConfig;
    private final RestTemplate template;

    @Autowired
    public FacebookApiService(FacebookConfig facebookConfig, RestTemplate template) {
        this.facebookConfig = facebookConfig;
        this.template = template;
    }

    public String getAccessTokenFromFacebook(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        ResponseEntity<FacebookGetAccessTokenResponseDto> response = template.exchange(
                getAccessTokenUrl(code),
                HttpMethod.GET,
                entity,
                FacebookGetAccessTokenResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "failed to login");
        }

        return response.getBody().getAccess_token();
    }

    private String getAccessTokenUrl(String code) {
        return FACEBOOK_API_ACCESS_TOKEN_URL + "?client_id=" + facebookConfig.getClientId() + "&code=" + code + "&client_secret=" + facebookConfig.getClientSecret() + "&redirect_uri=" + facebookConfig.getRedirectUri();
    }

}
