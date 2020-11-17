package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.UserDao;
import de.neuefische.finalproject.ohboy.dto.FacebookUserDto;
import de.neuefische.finalproject.ohboy.model.OhBoyUser;
import de.neuefische.finalproject.ohboy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FacebookLoginService {

    private final FacebookApiService apiService;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @Autowired
    public FacebookLoginService(FacebookApiService apiService, UserDao userDao, JwtUtils jwtUtils) {
        this.apiService = apiService;
        this.userDao = userDao;
        this.jwtUtils = jwtUtils;
    }

    public String getFacebookAccessToken(String code) {
        // get access token from facebook
        String accessToken = apiService.getAccessTokenFromFacebook(code);

        // get user data from user
        FacebookUserDto userData = apiService.getFacebookUserData(accessToken);

        // add / update user
        OhBoyUser ohBoyUser = saveUserData(userData);

        // generate jwt token
        return jwtUtils.createJwtToken(ohBoyUser.getId(), new HashMap<>(Map.of(
                "name", ohBoyUser.getName()
        )));
    }

    private OhBoyUser saveUserData(FacebookUserDto userData) {
        String userId = "facebook@" + userData.getId();
        Optional<OhBoyUser> existingUser = userDao.findById(userId);

        if (existingUser.isPresent()) {
            OhBoyUser ohBoyUser = existingUser.get();
            ohBoyUser.setName(userData.getName());
            userDao.save(ohBoyUser);
            return ohBoyUser;
        }
        OhBoyUser ohBoyUser = new OhBoyUser(
                userId,
                userData.getName(),
                true
        );
        userDao.save(ohBoyUser);
        return ohBoyUser;
    }
}

