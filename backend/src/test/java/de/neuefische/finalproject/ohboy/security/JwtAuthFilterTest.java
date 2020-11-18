package de.neuefische.finalproject.ohboy.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "jwt.secretkey=somesecrettoken",
        "oauth.facebook.client.id=facebookClient",
        "oauth.facebook.client.secret=secret",
        "oauth.facebook.redirect.uri=redirectUri",
})
class JwtAuthFilterTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String secretKey = "somesecrettoken";

    @Test
    public void getWithValidJwtTokenShouldReturn200Ok(){
        //GIVEN
        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("id123")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(2))))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        //WHEN
        String url = "http://localhost:" + port + "/api/monster";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getWithValidJwtTokenShouldReturnForbiddenWhenTokenIsExpired(){
        //GIVEN
        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("id123")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().minus(Duration.ofMinutes(2))))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        //WHEN
        String url = "http://localhost:" + port + "/api/monster";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void getWithValidJwtTokenShouldReturnForbiddenWhenSecretKeyNotMatch(){
        //GIVEN
        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("id123")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(2))))
                .signWith(SignatureAlgorithm.HS256,"other-key")
                .compact();

        //WHEN
        String url = "http://localhost:" + port + "/api/monster";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

}
