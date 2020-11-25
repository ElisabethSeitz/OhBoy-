package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.dto.AddTaskDto;
import de.neuefische.finalproject.ohboy.dto.UpdateMonsterDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Status;
import de.neuefische.finalproject.ohboy.model.Task;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    //Given
    final IdUtils idUtils = mock(IdUtils.class);
    final TimestampUtils timestampUtils = mock(TimestampUtils.class);
    final TaskMongoDao taskMongoDao = mock(TaskMongoDao.class);
    final MonsterMongoDao monsterMongoDao = mock(MonsterMongoDao.class);

    final TaskService taskService = new TaskService(taskMongoDao, idUtils, timestampUtils, monsterMongoDao);

    final List<Task> tasks = new ArrayList<>(List.of(
            new Task("someId", "someUserId", "someMonsterId", "someDescription", 5, DONE, Instant.parse("1970-01-01T00:00:00Z")),
            new Task("someId2", "someUserId2", "someMonsterId2", "someDescription2", 10, OPEN, Instant.parse("1970-01-01T00:00:00Z")),
            new Task("someId3", "someUserId3", "someMonsterId3", "someDescription3", 15, OPEN, Instant.parse("1970-01-01T00:00:00Z"))
    ));

    final List<Task> getStockTasks(){
        return tasks;
    }

    @Test
    @DisplayName("The \"findAllByMonsterId\" method should return all Task objects that match the MonsterId in a list")
    void findAllByMonsterId() {
        //Given
        String monsterId = "someMonsterId";

        List<Task> expectedTasks = new ArrayList<>(List.of(Task.builder()
                .id("id")
                .userId("someUserId")
                .monsterId(monsterId)
                .description("someDescription")
                .score(5)
                .status(DONE)
                .timestampOfDone(Instant.parse("1970-01-01T00:00:00Z"))
                .build()
        ));

        when(taskMongoDao.findAllByMonsterId(monsterId)).thenReturn(expectedTasks);

        //When
        List<Task> resultTasks = taskService.findAllByMonsterId(monsterId);

        //Then
        assertThat(resultTasks, is(expectedTasks));
    }

    @Test
    @DisplayName("The \"add\" method should return the added Task object")
    void add() {
        //Given
        String expectedId = "randomId";

        Monster monster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .build();

        AddTaskDto taskDto = new AddTaskDto(
                "some description",
                10
        );

        Task expectedTask = Task.builder()
                .id(expectedId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build();

        when(idUtils.generateId()).thenReturn(expectedId);
        when(taskMongoDao.save(expectedTask)).thenReturn(expectedTask);
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));

        //When
        Task newTask = taskService.add(taskDto, "someMonsterId", "someUserId");

        //Then
        assertThat(newTask, is(expectedTask));
        verify(taskMongoDao).save(expectedTask);
    }

    @Test
    @DisplayName("The \"add\" method should return bad request when monsterId not found")
    void addBadRequest() {
        //Given
        AddTaskDto taskDto = new AddTaskDto(
                "some description",
                10
        );

        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.empty());

        //When
        try {
            taskService.add(taskDto, "someMonsterId", "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.BAD_REQUEST));
        }
    }

    @Test
    @DisplayName("The \"add\" method should throw forbidden when user with not matching userId try to add a Task object")
    void addForbidden() {
        //Given
        Monster monster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .build();

        AddTaskDto taskDto = new AddTaskDto(
                "some description",
                10
        );

        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));

        //When
        try {
            taskService.add(taskDto, "someMonsterId", "someOtherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"add\" method should increase the countOpenTasks by 1")
    void addSetCountOpenTasks() {
        //Given
        Monster monster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .countOpenTasks(4)
                .build();

        AddTaskDto taskDto = new AddTaskDto(
                "some description",
                10
        );

        Monster expectedMonster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .countOpenTasks(5)
                .build();

        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));

        //When
        Task newTask = taskService.add(taskDto, "someMonsterId", "someUserId");

        //Then
        verify(monsterMongoDao).save(expectedMonster);
    }

}