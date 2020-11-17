package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dao.UserDao;
import de.neuefische.finalproject.ohboy.dto.FacebookCodeDto;
import de.neuefische.finalproject.ohboy.dto.FacebookConfigDto;
import de.neuefische.finalproject.ohboy.dto.FacebookGetAccessTokenResponseDto;
import de.neuefische.finalproject.ohboy.dto.FacebookUserDto;
import de.neuefische.finalproject.ohboy.model.OhBoyUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "jwt.secretkey=somesecrettoken",
        "oauth.facebook.client.id=facebookClient",
        "oauth.facebook.client.secret=secret",
        "oauth.facebook.redirect.uri=redirectUri",
})
class FacebookLoginControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RestTemplate mockServerRestTemplate;

    @Autowired
    private UserDao userDao;

    @Test
    public void getFacebookConfigShouldReturnClientIdAndRedirectUri() {
        //WHEN
        ResponseEntity<FacebookConfigDto> response = restTemplate.getForEntity("http://localhost:" + port + "/auth/login/facebook", FacebookConfigDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new FacebookConfigDto("facebookClient", "redirectUri")));
    }

    @Test
    public void getJwtTokenWithValidAccessCode(){

        // MOCK github access token response
        String accessUrl ="https://graph.facebook.com/v9.0/oauth/access_token?client_id=facebookClient&code=test-facebook-code&client_secret=secret&redirect_uri=redirectUri";
        FacebookGetAccessTokenResponseDto access_token = new FacebookGetAccessTokenResponseDto("access_token");
        ResponseEntity<FacebookGetAccessTokenResponseDto> responseMockAccessToken = new ResponseEntity<>(access_token,HttpStatus.OK);
        when(mockServerRestTemplate.exchange(eq(accessUrl),eq(HttpMethod.GET),any(), eq(FacebookGetAccessTokenResponseDto.class))).thenReturn(responseMockAccessToken);


        //MOCK github user response
        String userUrl ="https://graph.facebook.com/v9.0/me?access_token=access_token";

        when(mockServerRestTemplate.exchange(eq(userUrl), eq(HttpMethod.GET), any(), eq(FacebookUserDto.class))).thenReturn(
                new ResponseEntity<>(new FacebookUserDto(
                        "name",
                        "id123"
                ), HttpStatus.OK)
        );

        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/auth/login/facebook", new FacebookCodeDto("test-facebook-code"),String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Claims claims = Jwts.parser().setSigningKey("somesecrettoken").parseClaimsJws(response.getBody()).getBody();

        assertThat(claims.getSubject(), is("facebook@id123"));
        assertThat(claims.get("name"), is("name"));

        Optional<OhBoyUser> savedUser = userDao.findById("facebook@id123");
        assertThat(savedUser.get(), is(OhBoyUser
                .builder()
                .name("name")
                .id("facebook@id123")
                .facebookUser(true).build()
        ));

    }

}