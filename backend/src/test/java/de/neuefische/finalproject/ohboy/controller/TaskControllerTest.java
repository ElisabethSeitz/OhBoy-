package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.dao.UserDao;
import de.neuefische.finalproject.ohboy.dto.FacebookCodeDto;
import de.neuefische.finalproject.ohboy.dto.FacebookUserDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.OhBoyUser;
import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.service.FacebookApiService;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import de.neuefische.finalproject.ohboy.utils.TimestampUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static de.neuefische.finalproject.ohboy.model.Status.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "jwt.secretkey=somesecretkey",
        "oauth.facebook.client.id=facebookClient",
        "oauth.facebook.client.secret=secret",
        "oauth.facebook.redirect.uri=redirectUri",
})
class TaskControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private IdUtils mockedIdUtils;

    @MockBean
    private TimestampUtils mockedTimestampUtils;

    @MockBean
    private FacebookApiService mockedFacebookApiService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskMongoDao taskDao;

    @Autowired
    private MonsterMongoDao monsterDao;

    @BeforeEach
    public void setupTaskDao() {
        taskDao.deleteAll();
        taskDao.saveAll(List.of(
                new Task("someId", "someMonsterId", "someDescription", 5, DONE, Instant.parse("1970-01-01T00:00:00Z")),
                new Task("someId2", "someMonsterId2", "someDescription2", 10, OPEN, Instant.parse("1970-01-01T00:00:00Z")),
                new Task("someId3", "someMonsterId3", "someDescription3", 15, OPEN, Instant.parse("1970-01-01T00:00:00Z"))
        ));

        monsterDao.deleteAll();
        monsterDao.save(new Monster("someId2", "facebook@123", "someName2", "someImage2", 100, 50, 4, 3, 2, 6, 6));
    }

    private String getTasksUrl() {
        return "http://localhost:" + port + "/api/monster/tasks";
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
    public void testGetMappingForbiddenWhenNoValidJWT() {
        // GIVEN
        String url = getTasksUrl() + "/someMonsterId";

        // WHEN
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void testGetAllByMonsterIdMapping() {

        // GIVEN
        String url = getTasksUrl() + "/someMonsterId";

        // WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Task[]> response = restTemplate.exchange(url,HttpMethod.GET,entity, Task[].class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        List<Task> stockTasks = new ArrayList<>(List.of(
                    Task.builder()
                            .id("someId")
                            .monsterId("someMonsterId")
                            .description("someDescription")
                            .status(DONE)
                            .score(5)
                            .timestampOfDone(Instant.parse("1970-01-01T00:00:00Z"))
                            .build()
            ));

            assertThat(response.getBody(), is(stockTasks.toArray()));
        }
    }