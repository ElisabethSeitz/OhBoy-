package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.UserDao;
import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.dto.FacebookCodeDto;
import de.neuefische.finalproject.ohboy.dto.FacebookUserDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.OhBoyUser;
import de.neuefische.finalproject.ohboy.service.FacebookApiService;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "jwt.secretkey=somesecrettoken",
        "oauth.facebook.client.id=facebookClient",
        "oauth.facebook.client.secret=secret",
        "oauth.facebook.redirect.uri=redirectUri",
})
class MonsterControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private IdUtils mockedIdUtils;

    @MockBean
    private FacebookApiService mockedFacebookApiService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MonsterMongoDao monsterDao;

    @Autowired
    private UserDao userDao;


    @BeforeEach
    public void setupMonsterDao() {
        monsterDao.deleteAll();
        monsterDao.saveAll(List.of(
                new Monster("someId", "someUserId", "someName", "someImage"),
                new Monster("someId2", "facebook@123", "someName2", "someImage2"),
                new Monster("someId3", "someUserId3", "someName3", "someImage3")
        ));

        userDao.deleteAll();
        userDao.save(new OhBoyUser("facebook@123", "userName", true));
    }

    private String getMonstersUrl() {
        return "http://localhost:" + port + "/api/monster";
    }

    private String login(){
        when(mockedFacebookApiService.getAccessTokenFromFacebook("code")).thenReturn("access_token");
        when(mockedFacebookApiService.getFacebookUserData("access_token")).thenReturn(new FacebookUserDto("FacebookUser", "1234"));

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/auth/login/facebook", new FacebookCodeDto(
                "code"
        ), String.class);

        return response.getBody();
    }

    private <T> HttpEntity<T> getValidAuthorizationEntity(T data) {
        String token = login();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<T>(data,headers);
    }

    @Test
    public void testGetMapping() {
        //GIVEN
        List<Monster> stockMonsters = new ArrayList<>(List.of(
                new Monster("someId", "someUserId", "someName", "someImage"),
                new Monster("someId2", "facebook@123", "someName2", "someImage2"),
                new Monster("someId3", "someUserId3", "someName3", "someImage3")
        ));

        String url = getMonstersUrl();

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);

        ResponseEntity<Monster[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Monster[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(stockMonsters.toArray()));
    }

    @Test
    public void testGetMappingForbiddenWhenNoValidJWT() {
        // GIVEN
        String url = getMonstersUrl();
        // WHEN

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void testGetAllByUserIdMapping() {
        // GIVEN
        String url = getMonstersUrl() + "/someUserId";

        // WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Monster[]> response = restTemplate.exchange(url,HttpMethod.GET,entity, Monster[].class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        List<Monster> stockMonsters = new ArrayList<>(List.of(
                new Monster("someId", "someUserId", "someName", "someImage")
        ));
        assertThat(response.getBody(), is(stockMonsters.toArray()));
    }

    @Test
    public void postMonsterShouldAddANewMonster() {
        // GIVEN
        String url = getMonstersUrl();
        AddMonsterDto monsterToAdd = new AddMonsterDto(
                "some name",
                "facebook@someUserId",
                "some image"
        );
        when(mockedIdUtils.generateId()).thenReturn("some generated id");

        // WHEN
        HttpEntity<AddMonsterDto> entity = getValidAuthorizationEntity(monsterToAdd);
        ResponseEntity<Monster> response = restTemplate.exchange(url, HttpMethod.POST,entity, Monster.class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new Monster(
                "some generated id", "facebook@someUserId", "some name", "some image")
        ));
    }

}