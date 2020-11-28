package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.RewardMongoDao;
import de.neuefische.finalproject.ohboy.dto.*;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Reward;
import de.neuefische.finalproject.ohboy.model.Status;
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

import static de.neuefische.finalproject.ohboy.model.Status.DONE;
import static de.neuefische.finalproject.ohboy.model.Status.OPEN;
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
class RewardControllerTest {

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
    private RewardMongoDao rewardDao;

    @Autowired
    private MonsterMongoDao monsterDao;

    @BeforeEach
    public void setupDao() {
        rewardDao.deleteAll();
        rewardDao.saveAll(List.of(
                Reward.builder().id("someId").userId("facebook@1234").monsterId("someMonsterId").description("someDescription").score(5).status(DONE).timestampOfDone(Instant.parse("1970-01-01T00:00:00Z")).build(),
                Reward.builder().id("someId2").userId("facebook@1234").monsterId("someMonsterId2").description("someDescription2").score(10).status(OPEN).build(),
                Reward.builder().id("someId3").userId("someUserId3").monsterId("someMonsterId3").description("someDescription3").score(15).status(OPEN).build()
        ));

        monsterDao.deleteAll();
        monsterDao.saveAll(List.of(
                new Monster("someMonsterId2", "facebook@1234", "someName2", "someImage2", 100, 50, 4),
                new Monster("someMonsterId", "facebook@1234", "someName", "someImage", 10, 5, 15),
                new Monster("someMonsterId3", "someUserId3", "someName3", "someImage3", 25, 10, 30)));
    }

    private String getRewardUrl() {
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
        String url = getRewardUrl() + "someMonsterId/rewards";

        // WHEN
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void testGetAllByMonsterIdMapping() {

        // GIVEN
        String url = getRewardUrl() + "someMonsterId/rewards";

        // WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Reward[]> response = restTemplate.exchange(url, HttpMethod.GET,entity, Reward[].class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        List<Reward> stockRewards = new ArrayList<>(List.of(
                Reward.builder()
                        .id("someId")
                        .userId("facebook@1234")
                        .monsterId("someMonsterId")
                        .description("someDescription")
                        .status(DONE)
                        .score(5)
                        .timestampOfDone(Instant.parse("1970-01-01T00:00:00Z"))
                        .build()
        ));

        assertThat(response.getBody(), is(stockRewards.toArray()));
    }

    @Test
    public void postRewardShouldAddANewReward() {
        // GIVEN
        String url = getRewardUrl() + "someMonsterId2/tasks";
        AddRewardDto rewardToAdd = new AddRewardDto(
                "some description",
                10
        );
        when(mockedIdUtils.generateId()).thenReturn("some generated id");

        // WHEN
        HttpEntity<AddRewardDto> entity = getValidAuthorizationEntity(rewardToAdd);
        ResponseEntity<Reward> response = restTemplate.exchange(url, HttpMethod.POST,entity, Reward.class);

        // THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(Reward.builder()
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
    public void updateRewardShouldUpdateExistingReward() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId2/rewards/someId2";

        UpdateRewardDto updateReward = new UpdateRewardDto("someId2", "some updatedDescription", 20);

        //WHEN
        HttpEntity<UpdateRewardDto> entity = getValidAuthorizationEntity(updateReward);
        ResponseEntity<Reward> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Reward.class);

        //THEN
        Optional<Reward> savedTask = rewardDao.findById("someId2");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Reward expectedReward = Reward.builder()
                .id("someId2")
                .userId("facebook@1234")
                .monsterId("someMonsterId2")
                .description("some updatedDescription")
                .score(20)
                .status(Status.OPEN)
                .build();

        assertThat(response.getBody(), is(expectedReward));
        assertThat(savedTask.get(), is(expectedReward));
    }

    @Test
    public void updateRewardWhenNoExistingRewardShouldReturnNotFound() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId2/rewards/someIdXY";

        UpdateRewardDto updateReward = new UpdateRewardDto("someIdXY", "some updatedDescription", 50);

        //WHEN
        HttpEntity<UpdateRewardDto> entity = getValidAuthorizationEntity(updateReward);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void updateRewardWithNotMatchingUserIdShouldReturnForbidden() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId3/rewards/someId3";

        UpdateRewardDto updateReward = new UpdateRewardDto("someId3", "some updatedDescription", 20);

        //WHEN
        HttpEntity<UpdateRewardDto> entity = getValidAuthorizationEntity(updateReward);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        Optional<Reward> savedReward = rewardDao.findById("someId3");
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

        Reward expectedReward = Reward.builder()
                .id("someId3")
                .userId("someUserId3")
                .monsterId("someMonsterId3")
                .description("someDescription3")
                .score(15)
                .status(Status.OPEN)
                .build();

        assertThat(savedReward.get(), is(expectedReward));
    }

    @Test
    public void updateRewardWithStatusDoneShouldReturnForbidden() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId/rewards/someId";

        UpdateRewardDto updateReward = new UpdateRewardDto("someId", "some updatedDescription", 20);

        //WHEN
        HttpEntity<UpdateRewardDto> entity = getValidAuthorizationEntity(updateReward);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        Optional<Reward> savedReward = rewardDao.findById("someId");
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

        Reward expectedReward = Reward.builder()
                .id("someId")
                .userId("facebook@1234")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(5)
                .status(Status.DONE)
                .timestampOfDone(Instant.parse("1970-01-01T00:00:00Z"))
                .build();

        assertThat(savedReward.get(), is(expectedReward));
    }

    @Test
    public void updateRewardWithNotMatchingIdsShouldReturnBadRequest() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId2/rewards/someId2";

        UpdateRewardDto updateReward = new UpdateRewardDto("someId3", "some updatedDescription", 20);

        //WHEN
        HttpEntity<UpdateRewardDto> entity = getValidAuthorizationEntity(updateReward);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void removeRewardShouldRemoveExistingReward() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId2/rewards/someId2";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        boolean rewardPresent = rewardDao.findById("someId2").isPresent();
        assertThat(rewardPresent, is(false));
    }

    @Test
    public void removeRewardWithNotMatchingUserIdShouldReturnForbidden() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId3/rewards/someId3";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        boolean rewardPresent = rewardDao.findById("someId3").isPresent();
        assertThat(rewardPresent, is(true));
    }

    @Test
    public void removeRewardWithStatusDoneShouldReturnForbidden() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId/rewards/someId";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        boolean rewardPresent = rewardDao.findById("someId").isPresent();
        assertThat(rewardPresent, is(true));
    }

    @Test
    public void removeRewardWhenNoExistingRewardShouldReturnNotFound() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId/rewards/someIdXY";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void updateRewardStatusInDoneShouldUpdateExistingReward() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId2/rewards/someId2/status";

        when(mockedTimestampUtils.generateTimeStampEpochSeconds()).thenReturn(Instant.parse("2018-11-30T18:35:24.00Z"));

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Reward> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Reward.class);

        //THEN
        Optional<Reward> savedReward = rewardDao.findById("someId2");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Reward expectedReward = Reward.builder()
                .id("someId2")
                .userId("facebook@1234")
                .monsterId("someMonsterId2")
                .description("someDescription2")
                .score(10)
                .status(Status.DONE)
                .timestampOfDone(Instant.parse("2018-11-30T18:35:24.00Z"))
                .build();

        assertThat(response.getBody(), is(expectedReward));
        assertThat(savedReward.get(), is(expectedReward));
    }

    @Test
    public void updateRewardStatusInOpenShouldUpdateExistingReward() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId/rewards/someId/status";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Reward> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Reward.class);

        //THEN
        Optional<Reward> savedReward = rewardDao.findById("someId");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Reward expectedReward = Reward.builder()
                .id("someId")
                .userId("facebook@1234")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(5)
                .status(Status.OPEN)
                .timestampOfDone(null)
                .build();

        assertThat(response.getBody(), is(expectedReward));
        assertThat(savedReward.get(), is(expectedReward));
    }

    @Test
    public void updateRewardStatusInDoneShouldUpdateExistingMonster() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId2/rewards/someId2/status";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Reward> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Reward.class);

        //THEN
        Optional<Monster> savedMonster = monsterDao.findById("someMonsterId2");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Monster expectedMonster = Monster.builder()
                .id("someMonsterId2")
                .userId("facebook@1234")
                .name("someName2")
                .image("someImage2")
                .scoreDoneTasks(4)
                .payoutDoneRewards(60)
                .balance(100)
                .build();

        assertThat(savedMonster.get(), is(expectedMonster));
    }

    @Test
    public void updateRewardStatusInOpenShouldUpdateExistingMonster() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId/rewards/someId/status";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Reward> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Reward.class);

        //THEN
        Optional<Monster> savedMonster = monsterDao.findById("someMonsterId");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Monster expectedMonster = Monster.builder()
                .id("someMonsterId")
                .userId("facebook@1234")
                .name("someName")
                .image("someImage")
                .scoreDoneTasks(15)
                .payoutDoneRewards(0)
                .balance(10)
                .build();

        assertThat(savedMonster.get(), is(expectedMonster));
    }

    @Test
    public void updateRewardStatusWhenNoExistingRewardShouldReturnNotFound() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId2/rewards/someIdXY/status";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void updateRewardStatusWhenNoExistingMonsterShouldReturnNotFound() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterIdXY/rewards/someId2/status";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void updateRewardStatusWithNotMatchingUserIdShouldReturnForbidden() {
        //GIVEN
        String url = getRewardUrl() + "someMonsterId3/rewards/someId3/status";

        //WHEN
        HttpEntity<Void> entity = getValidAuthorizationEntity(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        //THEN
        Optional<Reward> savedReward = rewardDao.findById("someId3");
        Optional<Monster> savedMonster = monsterDao.findById("someMonsterId3");
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

        Reward expectedReward = Reward.builder()
                .id("someId3")
                .userId("someUserId3")
                .monsterId("someMonsterId3")
                .description("someDescription3")
                .score(15)
                .status(OPEN)
                .build();

        Monster expectedMonster = Monster.builder()
                .id("someMonsterId3")
                .userId("someUserId3")
                .name("someName3")
                .image("someImage3")
                .balance(25)
                .payoutDoneRewards(10)
                .scoreDoneTasks(30)
                .build();

        assertThat(savedReward.get(), is(expectedReward));
        assertThat(savedMonster.get(), is(expectedMonster));
    }
}
