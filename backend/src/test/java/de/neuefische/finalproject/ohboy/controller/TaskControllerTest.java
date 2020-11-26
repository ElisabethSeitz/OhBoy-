package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.dto.*;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Status;
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
import java.util.Optional;

import static de.neuefische.finalproject.ohboy.model.Status.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
                Task.builder().id("someId").userId("facebook@1234").monsterId("someMonsterId").description("someDescription").score(5).status(DONE).timestampOfDone(Instant.parse("1970-01-01T00:00:00Z")).build(),
                Task.builder().id("someId2").userId("facebook@1234").monsterId("someMonsterId2").description("someDescription2").score(10).status(OPEN).build(),
                Task.builder().id("someId3").userId("someUserId3").monsterId("someMonsterId3").description("someDescription3").score(15).status(OPEN).build()
        ));

        monsterDao.deleteAll();
        monsterDao.save(new Monster("someMonsterId2", "facebook@1234", "someName2", "someImage2", 100, 50, 4));
    }

    private String getTasksUrl() {
        return "http://localhost:" + port + "/api/monster/";
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
        String url = getTasksUrl() + "someMonsterId/tasks";

        // WHEN
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void testGetAllByMonsterIdMapping() {

        // GIVEN
        String url = getTasksUrl() + "someMonsterId/tasks";

        // WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Task[]> response = restTemplate.exchange(url,HttpMethod.GET,entity, Task[].class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        List<Task> stockTasks = new ArrayList<>(List.of(
                    Task.builder()
                            .id("someId")
                            .userId("facebook@1234")
                            .monsterId("someMonsterId")
                            .description("someDescription")
                            .status(DONE)
                            .score(5)
                            .timestampOfDone(Instant.parse("1970-01-01T00:00:00Z"))
                            .build()
            ));

            assertThat(response.getBody(), is(stockTasks.toArray()));
        }

    @Test
    public void postTaskShouldAddANewTask() {
        // GIVEN
        String url = getTasksUrl() + "someMonsterId2/tasks";
        AddTaskDto taskToAdd = new AddTaskDto(
                "some description",
                10
        );
        when(mockedIdUtils.generateId()).thenReturn("some generated id");

        // WHEN
        HttpEntity<AddTaskDto> entity = getValidAuthorizationEntity(taskToAdd);
        ResponseEntity<Task> response = restTemplate.exchange(url, HttpMethod.POST,entity, Task.class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(Task.builder()
                .id("some generated id")
                .userId("facebook@1234")
                .monsterId("someMonsterId2")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build()
        ));
    }

    @Test
    public void updateTaskShouldUpdateExistingTask() {
        //GIVEN
        String url = getTasksUrl() + "someMonsterId2/tasks/someId2";

        UpdateTaskDto updateTask = new UpdateTaskDto("someId2", "some updatedDescription", 20);

        //WHEN
        HttpEntity<UpdateTaskDto> entity = getValidAuthorizationEntity(updateTask);
        ResponseEntity<Task> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Task.class);

        //THEN
        Optional<Task> savedTask = taskDao.findById("someId2");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Task expectedTask = Task.builder()
                .id("someId2")
                .userId("facebook@1234")
                .monsterId("someMonsterId2")
                .description("some updatedDescription")
                .score(20)
                .status(Status.OPEN)
                .build();

        assertThat(response.getBody(), is(expectedTask));
        assertThat(savedTask.get(), is(expectedTask));
    }

    @Test
    public void updateTaskWhenNoExistingTaskShouldReturnNotFound() {
        //GIVEN
        String url = getTasksUrl() + "someMonsterId2/tasks/someIdXY";

        UpdateTaskDto updateTask = new UpdateTaskDto("someIdXY", "some updatedDescription", 50);

        //WHEN
        HttpEntity<UpdateTaskDto> entity = getValidAuthorizationEntity(updateTask);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void updateTaskWithNotMatchingUserIdShouldReturnForbidden() {
        //GIVEN
        String url = getTasksUrl() + "someMonsterId3/tasks/someId3";

        UpdateTaskDto updateTask = new UpdateTaskDto("someId3", "some updatedDescription", 20);

        //WHEN
        HttpEntity<UpdateTaskDto> entity = getValidAuthorizationEntity(updateTask);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        Optional<Task> savedTask = taskDao.findById("someId3");
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

        Task expectedTask = Task.builder()
                .id("someId3")
                .userId("someUserId3")
                .monsterId("someMonsterId3")
                .description("someDescription3")
                .score(15)
                .status(Status.OPEN)
                .build();

        assertThat(savedTask.get(), is(expectedTask));
    }

    @Test
    public void updateTaskWithStatusDoneShouldReturnForbidden() {
        //GIVEN
        String url = getTasksUrl() + "someMonsterId/tasks/someId";

        UpdateTaskDto updateTask = new UpdateTaskDto("someId", "some updatedDescription", 20);

        //WHEN
        HttpEntity<UpdateTaskDto> entity = getValidAuthorizationEntity(updateTask);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        Optional<Task> savedTask = taskDao.findById("someId");
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

        Task expectedTask = Task.builder()
                .id("someId")
                .userId("facebook@1234")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(5)
                .status(Status.DONE)
                .timestampOfDone(Instant.parse("1970-01-01T00:00:00Z"))
                .build();

        assertThat(savedTask.get(), is(expectedTask));
    }

    @Test
    public void updateTaskWithNotMatchingIdsShouldReturnBadRequest() {
        //GIVEN
        String url = getTasksUrl() + "someMonsterId2/tasks/someId2";

        UpdateTaskDto updateTask = new UpdateTaskDto("someId3", "some updatedDescription", 20);

        //WHEN
        HttpEntity<UpdateTaskDto> entity = getValidAuthorizationEntity(updateTask);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}