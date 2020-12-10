package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.RewardMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddRewardDto;
import de.neuefische.finalproject.ohboy.dto.UpdateRewardDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Reward;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import de.neuefische.finalproject.ohboy.utils.TimestampUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.neuefische.finalproject.ohboy.model.Status.DONE;
import static de.neuefische.finalproject.ohboy.model.Status.OPEN;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class RewardServiceTest {
    //Given
    final IdUtils idUtils = mock(IdUtils.class);
    final TimestampUtils timestampUtils = mock(TimestampUtils.class);
    final RewardMongoDao rewardMongoDao = mock(RewardMongoDao.class);
    final MonsterMongoDao monsterMongoDao = mock(MonsterMongoDao.class);

    final RewardService rewardService = new RewardService(rewardMongoDao, idUtils, timestampUtils, monsterMongoDao);

    @Test
    @DisplayName("The \"findAllByMonsterId\" method should return all Reward objects that match the MonsterId in a list")
    void findAllByMonsterId() {
        //Given
        String monsterId = "someMonsterId";

        List<Reward> expectedRewards = new ArrayList<>(List.of(Reward.builder()
                .id("id")
                .userId("someUserId")
                .monsterId(monsterId)
                .description("someDescription")
                .score(5)
                .status(DONE)
                .timestampOfDone(Instant.parse("1970-01-01T00:00:00Z"))
                .build()
        ));

        when(rewardMongoDao.findAllByMonsterId(monsterId)).thenReturn(expectedRewards);

        //When
        List<Reward> resultRewards = rewardService.findAllByMonsterId(monsterId);

        //Then
        assertThat(resultRewards, is(expectedRewards));
    }

    @Test
    @DisplayName("The \"add\" method should return the added Reward object")
    void add() {
        //Given
        String expectedId = "randomId";

        Monster monster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .build();

        AddRewardDto rewardDto = new AddRewardDto(
                "some description",
                10
        );

        Reward expectedReward = Reward.builder()
                .id(expectedId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build();

        when(idUtils.generateId()).thenReturn(expectedId);
        when(rewardMongoDao.save(expectedReward)).thenReturn(expectedReward);
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));

        //When
        Reward newReward = rewardService.add(rewardDto, "someMonsterId", "someUserId");

        //Then
        assertThat(newReward, is(expectedReward));
        verify(rewardMongoDao).save(expectedReward);
    }

    @Test
    @DisplayName("The \"add\" method should return bad request when monsterId not found")
    void addBadRequest() {
        //Given
        AddRewardDto rewardDto = new AddRewardDto(
                "some description",
                10
        );

        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.empty());

        //When
        try {
            rewardService.add(rewardDto, "someMonsterId", "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.BAD_REQUEST));
        }
    }

    @Test
    @DisplayName("The \"add\" method should throw forbidden when user with not matching userId try to add a Reward object")
    void addForbidden() {
        //Given
        Monster monster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .build();

        AddRewardDto rewardDto = new AddRewardDto(
                "some description",
                10
        );

        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));

        //When
        try {
            rewardService.add(rewardDto, "someMonsterId", "someOtherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"update\" method should return the updated Reward object")
    void update() {
        //Given
        String rewardId = "randomId";

        UpdateRewardDto rewardDto = new UpdateRewardDto(
                rewardId,
                "some updatedDescription",
                20
        );

        Reward reward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build();

        Reward updatedReward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some updatedDescription")
                .score(20)
                .status(OPEN)
                .build();

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(reward));
        when(rewardMongoDao.save(updatedReward)).thenReturn(updatedReward);

        //When
        Reward result = rewardService.update(rewardDto, "someUserId");

        //Then
        assertThat(result, is(updatedReward));
        verify(rewardMongoDao).save(updatedReward);
    }

    @Test
    @DisplayName("The \"update\" method should throw forbidden when user with not matching userId try to modify a Reward object")
    void updateForbiddenUserId() {
        //Given
        String rewardId = "randomId";

        UpdateRewardDto rewardDto = new UpdateRewardDto(
                rewardId,
                "some updatedDescription",
                20
        );

        Reward reward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build();

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(reward));

        //When
        try {
            rewardService.update(rewardDto, "some otherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"update\" method should throw forbidden when a Reward object with Status DONE should be modified")
    void updateForbiddenStatusDone() {
        //Given
        String rewardId = "randomId";

        UpdateRewardDto rewardDto = new UpdateRewardDto(
                rewardId,
                "some updatedDescription",
                20
        );

        Reward reward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(DONE)
                .build();

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(reward));

        //When
        try {
            rewardService.update(rewardDto, "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"update\" method should throw not found when id not found")
    void updateNotFound() {
        //Given
        String rewardId = "randomId";

        UpdateRewardDto rewardDto = new UpdateRewardDto(
                rewardId,
                "some updatedDescription",
                20
        );

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.empty());

        //When
        try {
            rewardService.update(rewardDto, "some userId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    @DisplayName("The \"remove\" method should remove the removed Reward object")
    void remove() {
        //Given
        String rewardId = "randomId";

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(Reward.builder()
                .id(rewardId)
                .userId("some userId")
                .monsterId("some monsterId")
                .description("some description")
                .score(5)
                .status(OPEN)
                .build()));

        //When
        rewardService.remove(rewardId,"some userId");

        //Then
        verify(rewardMongoDao).deleteById(rewardId);
    }

    @Test
    @DisplayName("The \"remove\" method should throw forbidden when user with not matching userId try to remove a Reward object")
    void removeForbiddenUserId() {
        //Given
        String rewardId = "randomId";

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(Reward.builder()
                .id(rewardId)
                .userId("some userId")
                .monsterId("some monsterId")
                .description("some description")
                .score(5)
                .status(OPEN)
                .build()));

        //When
        try {
            rewardService.remove(rewardId, "some otherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"remove\" method should throw forbidden when Reward object with Status DONE should be removed")
    void removeForbiddenStatusDone() {
        //Given
        String rewardId = "randomId";

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(Reward.builder()
                .id(rewardId)
                .userId("some userId")
                .monsterId("some monsterId")
                .description("some description")
                .score(5)
                .status(DONE)
                .build()));

        //When
        try {
            rewardService.remove(rewardId, "some userId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"remove\" method should throw not found when id not found")
    void removeNotFound() {
        //Given
        String rewardId = "randomId";

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.empty());

        //When
        try {
            rewardService.remove(rewardId, "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    @DisplayName("The \"updateStatus\" method should return the updated Reward object")
    void updateStatusInDone() {
        //Given
        String rewardId = "randomId";
        Instant expectedTime = Instant.parse("2020-10-26T10:00:00Z");

        Reward reward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(10)
                .status(OPEN)
                .build();

        Reward updatedReward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(10)
                .status(DONE)
                .timestampOfDone(expectedTime)
                .build();

        Monster monster = Monster.builder()
                .id("someMonsterId")
                .build();

        when(timestampUtils.generateTimeStampEpochSeconds()).thenReturn(expectedTime);
        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(reward));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));
        when(rewardMongoDao.save(updatedReward)).thenReturn(updatedReward);

        //When
        Reward result = rewardService.updateStatus(rewardId, "someMonsterId", "someUserId");

        //Then
        assertThat(result, is(updatedReward));
        verify(rewardMongoDao).save(updatedReward);
    }

    @Test
    @DisplayName("The \"updateStatus\" method should return the updated Reward object")
    void updateStatusInOpen() {
        //Given
        String rewardId = "randomId";
        Instant savedTime = Instant.parse("2020-10-26T10:00:00Z");

        Reward reward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(10)
                .status(DONE)
                .timestampOfDone(savedTime)
                .build();

        Reward updatedReward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(10)
                .status(OPEN)
                .timestampOfDone(null)
                .build();

        Monster monster = Monster.builder()
                .id("someMonsterId")
                .build();

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(reward));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));
        when(rewardMongoDao.save(updatedReward)).thenReturn(updatedReward);

        //When
        Reward result = rewardService.updateStatus(rewardId, "someMonsterId", "someUserId");

        //Then
        assertThat(result, is(updatedReward));
        verify(rewardMongoDao).save(updatedReward);
    }

    @Test
    @DisplayName("The \"updateStatus\" method should save the updated Monster object")
    void updateStatusInDoneMonster() {
        //Given
        String rewardId = "randomId";

        Reward reward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(10)
                .status(OPEN)
                .build();

        Monster monster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .name("someName")
                .image("someImage")
                .scoreDoneTasks(0)
                .payoutDoneRewards(0)
                .build();

        Monster updatedMonster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .name("someName")
                .image("someImage")
                .scoreDoneTasks(0)
                .payoutDoneRewards(10)
                .build();

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(reward));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));
        when(monsterMongoDao.save(updatedMonster)).thenReturn(updatedMonster);

        //When
        rewardService.updateStatus(rewardId, "someMonsterId", "someUserId");

        //Then
        verify(monsterMongoDao).save(updatedMonster);
    }

    @Test
    @DisplayName("The \"updateStatus\" method should save the updated Monster object")
    void updateStatusInOpenMonster() {
        //Given
        String rewardId = "randomId";

        Reward reward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(10)
                .status(DONE)
                .build();

        Monster monster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .name("someName")
                .image("someImage")
                .scoreDoneTasks(0)
                .payoutDoneRewards(25)
                .build();

        Monster updatedMonster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .name("someName")
                .image("someImage")
                .scoreDoneTasks(0)
                .payoutDoneRewards(15)
                .build();

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(reward));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));
        when(monsterMongoDao.save(updatedMonster)).thenReturn(updatedMonster);

        //When
        rewardService.updateStatus(rewardId, "someMonsterId", "someUserId");

        //Then
        verify(monsterMongoDao).save(updatedMonster);
    }

    @Test
    @DisplayName("The \"updateStatus\" method should throw forbidden when user with not matching userId try to modify a Reward object")
    void updateStatusForbiddenUserId() {
        //Given
        String rewardId = "randomId";

        Reward reward = Reward.builder()
                .id(rewardId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build();

        Monster monster = Monster.builder()
                .id("someMonsterId")
                .build();

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.of(reward));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));

        //When
        try {
            rewardService.updateStatus(rewardId, "someMonsterId", "some otherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"updateStatus\" method should throw not found when rewardId not found")
    void updateStatusTaskIdNotFound() {
        //Given
        String rewardId = "randomId";

        when(rewardMongoDao.findById(rewardId)).thenReturn(Optional.empty());

        //When
        try {
            rewardService.updateStatus(rewardId, "someMonsterId", "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    @DisplayName("The \"updateStatus\" method should throw not found when monsterId not found")
    void updateStatusMonsterIdNotFound() {
        //Given
        String taskId = "randomId";

        when(monsterMongoDao.findById(taskId)).thenReturn(Optional.empty());

        //When
        try {
            rewardService.updateStatus(taskId, "someMonsterId", "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }
}

