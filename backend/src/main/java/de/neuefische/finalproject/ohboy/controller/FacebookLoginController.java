package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.config.FacebookConfig;
import de.neuefische.finalproject.ohboy.dto.FacebookCodeDto;
import de.neuefische.finalproject.ohboy.dto.FacebookConfigDto;
import de.neuefische.finalproject.ohboy.service.FacebookLoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/login/facebook")
public class FacebookLoginController {

    private final FacebookLoginService facebookLoginService;
    private final FacebookConfig facebookConfig;

    public FacebookLoginController(FacebookLoginService facebookLoginService, FacebookConfig facebookConfig) {
        this.facebookLoginService = facebookLoginService;
        this.facebookConfig = facebookConfig;
    }

    @PostMapping
    public String loginWithFacebook (@RequestBody FacebookCodeDto dto) {
        return facebookLoginService.getFacebookAccessToken(dto.getCode());
    }

    @GetMapping
    public FacebookConfigDto getConfig(){
        return new FacebookConfigDto(
                facebookConfig.getClientId(),
                facebookConfig.getRedirectUri());
    }
}
