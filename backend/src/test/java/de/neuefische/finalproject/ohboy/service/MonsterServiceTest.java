package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.dto.UpdateMonsterDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class MonsterServiceTest {

    //Given
    final IdUtils idUtils = mock(IdUtils.class);
    final MonsterMongoDao monsterMongoDao = mock(MonsterMongoDao.class);
    final TaskMongoDao taskMongoDao = mock(TaskMongoDao.class);

    final MonsterService monsterService = new MonsterService(monsterMongoDao, idUtils, taskMongoDao);

    @Test
    @DisplayName("The \"findAllByUserId\" method should return all Monster objects that match the UserId in a list")
    void findAllByUserId() {
        //Given
        String userId = "@facebookSomeUserId";

        List<Monster> expectedMonsters = new ArrayList<>(List.of(Monster.builder()
                .id("id")
                .userId(userId)
                .name("some name")
                .image("some image")
                .balance(0)
                .scoreDoneTasks(0)
                .payoutDoneRewards(0)
                .build()
        ));

        when(monsterMongoDao.findAllByUserId(userId)).thenReturn(expectedMonsters);

        //When
        List<Monster> resultMonsters = monsterService.findAllByUserId(userId);

        //Then
        assertThat(resultMonsters, is(expectedMonsters));
    }

    @Test
    @DisplayName("The \"add\" method should return the added Monster object")
    void add() {
        //Given
        String expectedId = "randomId";

        AddMonsterDto monsterDto = new AddMonsterDto(
                "some name",
                "some image"
        );

        Monster expectedMonster = Monster.builder()
               .id(expectedId)
               .userId("some UserId")
               .name("some name")
               .image("some image")
               .balance(0)
               .scoreDoneTasks(0)
               .payoutDoneRewards(0)
               .build();

        when(idUtils.generateId()).thenReturn(expectedId);
        when(monsterMongoDao.save(expectedMonster)).thenReturn(expectedMonster);

        //When
        Monster newMonster = monsterService.add(monsterDto, "some UserId");

        //Then
        assertThat(newMonster, is(expectedMonster));
        verify(monsterMongoDao).save(expectedMonster);
    }

    @Test
    @DisplayName("The \"update\" method should return the updated Monster object")
    void update() {
        //Given
        String monsterId = "randomId";

        UpdateMonsterDto monsterDto = new UpdateMonsterDto(
                monsterId,
                "some updatedName",
                "some updatedImage"
        );

        Monster monster = Monster.builder()
                .id(monsterId)
                .userId("some userId")
                .name("some name")
                .image("some image")
                .balance(5)
                .payoutDoneRewards(10)
                .scoreDoneTasks(20)
                .build();

        Monster updatedMonster = Monster.builder()
                .id(monsterId)
                .userId("some userId")
                .name("some updatedName")
                .image("some updatedImage")
                .balance(5)
                .payoutDoneRewards(10)
                .scoreDoneTasks(20)
                .build();

        when(monsterMongoDao.findById(monsterId)).thenReturn(Optional.of(monster));
        when(monsterMongoDao.save(updatedMonster)).thenReturn(updatedMonster);

        //When
        Monster result = monsterService.update(monsterDto, "some userId");

        //Then
        assertThat(result, is(updatedMonster));
        verify(monsterMongoDao).save(updatedMonster);
    }

    @Test
    @DisplayName("The \"update\" method should throw forbidden when user with not matching userId try to modify a Monster object")
    void updateForbidden() {
        //Given
        String monsterId = "randomId";

        UpdateMonsterDto monsterDto = new UpdateMonsterDto(
                monsterId,
                "some updatedName",
                "some updatedImage"
        );

        Monster monster = Monster.builder()
                .id(monsterId)
                .userId("some userId")
                .name("some name")
                .image("some image")
                .balance(5)
                .payoutDoneRewards(10)
                .scoreDoneTasks(20)
                .build();

        when(monsterMongoDao.findById(monsterId)).thenReturn(Optional.of(monster));

        //When
        try {
            monsterService.update(monsterDto, "some otherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"update\" method should throw not found when id not found")
    void updateNotFound() {
        //Given
        String monsterId = "randomId";

        UpdateMonsterDto monsterDto = new UpdateMonsterDto(
                monsterId,
                "some updatedName",
                "some updatedImage"
        );

        when(monsterMongoDao.findById(monsterId)).thenReturn(Optional.empty());

        //When
        try {
            monsterService.update(monsterDto, "some userId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    @DisplayName("The \"remove\" method should remove the removed Monster object")
    void remove() {
        //Given
        String monsterId = "randomId";

        when(monsterMongoDao.findById(monsterId)).thenReturn(Optional.of(Monster.builder()
                .id(monsterId)
                .userId("some userId")
                .name("some name")
                .image("some image")
                .balance(5)
                .payoutDoneRewards(10)
                .scoreDoneTasks(20)
                .build()));

        //When
        monsterService.remove(monsterId,"some userId");

        //Then
        verify(monsterMongoDao).deleteById(monsterId);
    }

    @Test
    @DisplayName("The \"remove\" method should remove the related Task objects")
    void removeRelatedTasks() {
        //Given
        String monsterId = "randomId";

        when(monsterMongoDao.findById(monsterId)).thenReturn(Optional.of(Monster.builder()
                .id(monsterId)
                .userId("some userId")
                .name("some name")
                .image("some image")
                .balance(5)
                .payoutDoneRewards(10)
                .scoreDoneTasks(20)
                .build()));

        when(taskMongoDao.findAllByMonsterId(monsterId)).thenReturn(List.of(Task.builder()
                .id("someId")
                .monsterId(monsterId)
                .userId("some userId")
                .description("some description")
                .score(10)
                .build()));

        //When
        monsterService.remove(monsterId,"some userId");

        //Then
        verify(taskMongoDao).deleteAll(List.of(Task.builder()
                .id("someId")
                .monsterId(monsterId)
                .userId("some userId")
                .description("some description")
                .score(10)
                .build()));
    }

    @Test
    @DisplayName("The \"remove\" method should throw forbidden when user with not matching userId try to remove a Monster object")
    void removeForbidden() {
        //Given
        String monsterId = "randomId";

        when(monsterMongoDao.findById(monsterId)).thenReturn(Optional.of(Monster.builder()
                .id(monsterId)
                .userId("some userId")
                .name("some name")
                .image("some image")
                .balance(5)
                .payoutDoneRewards(10)
                .scoreDoneTasks(20)
                .build()));

        //When
        try {
            monsterService.remove(monsterId, "some otherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"remove\" method should throw not found when id not found")
    void removeNotFound() {
        //Given
        String monsterId = "randomId";

        when(monsterMongoDao.findById(monsterId)).thenReturn(Optional.empty());

        //When
        try {
            monsterService.remove(monsterId, "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }
}