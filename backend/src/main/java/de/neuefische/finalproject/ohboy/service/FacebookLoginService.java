package de.neuefische.finalproject.ohboy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacebookLoginService {

    private final FacebookApiService apiService;

    @Autowired
    public FacebookLoginService(FacebookApiService apiService) {
        this.apiService = apiService;
    }


    public String getFacebookAccessToken(String code) {
        // get access token from github
        String accessToken = apiService.getAccessTokenFromFacebook(code);

        // generate jwt token
        return accessToken;
    }
}

