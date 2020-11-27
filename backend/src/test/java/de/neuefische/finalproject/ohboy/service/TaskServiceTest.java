package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddTaskDto;
import de.neuefische.finalproject.ohboy.dto.UpdateTaskDto;
import de.neuefische.finalproject.ohboy.model.Monster;
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
import static org.mockito.Mockito.*;

class TaskServiceTest {
    //Given
    final IdUtils idUtils = mock(IdUtils.class);
    final TimestampUtils timestampUtils = mock(TimestampUtils.class);
    final TaskMongoDao taskMongoDao = mock(TaskMongoDao.class);
    final MonsterMongoDao monsterMongoDao = mock(MonsterMongoDao.class);

    final TaskService taskService = new TaskService(taskMongoDao, idUtils, timestampUtils, monsterMongoDao);

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
    @DisplayName("The \"update\" method should return the updated Task object")
    void update() {
        //Given
        String taskId = "randomId";

        UpdateTaskDto taskDto = new UpdateTaskDto(
                taskId,
                "some updatedDescription",
                20
        );

        Task task = Task.builder()
                .id(taskId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build();

        Task updatedTask = Task.builder()
                .id(taskId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some updatedDescription")
                .score(20)
                .status(OPEN)
                .build();

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMongoDao.save(updatedTask)).thenReturn(updatedTask);

        //When
        Task result = taskService.update(taskDto, "someUserId");

        //Then
        assertThat(result, is(updatedTask));
        verify(taskMongoDao).save(updatedTask);
    }

    @Test
    @DisplayName("The \"update\" method should throw forbidden when user with not matching userId try to modify a Task object")
    void updateForbiddenUserId() {
        //Given
        String taskId = "randomId";

        UpdateTaskDto taskDto = new UpdateTaskDto(
                taskId,
                "some updatedDescription",
                20
        );

        Task task = Task.builder()
                .id(taskId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build();

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(task));

        //When
        try {
            taskService.update(taskDto, "some otherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"update\" method should throw forbidden when a Task object with Status DONE should be modified")
    void updateForbiddenStatusDone() {
        //Given
        String taskId = "randomId";

        UpdateTaskDto taskDto = new UpdateTaskDto(
                taskId,
                "some updatedDescription",
                20
        );

        Task task = Task.builder()
                .id(taskId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(DONE)
                .build();

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(task));

        //When
        try {
            taskService.update(taskDto, "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"update\" method should throw not found when id not found")
    void updateNotFound() {
        //Given
        String taskId = "randomId";

        UpdateTaskDto taskDto = new UpdateTaskDto(
                taskId,
                "some updatedDescription",
                20
        );

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.empty());

        //When
        try {
            taskService.update(taskDto, "some userId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    @DisplayName("The \"remove\" method should remove the removed Task object")
    void remove() {
        //Given
        String taskId = "randomId";

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(Task.builder()
                .id(taskId)
                .userId("some userId")
                .monsterId("some monsterId")
                .description("some description")
                .score(5)
                .status(OPEN)
                .build()));

        //When
        taskService.remove(taskId,"some userId");

        //Then
        verify(taskMongoDao).deleteById(taskId);
    }

    @Test
    @DisplayName("The \"remove\" method should throw forbidden when user with not matching userId try to remove a Task object")
    void removeForbiddenUserId() {
        //Given
        String taskId = "randomId";

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(Task.builder()
                .id(taskId)
                .userId("some userId")
                .monsterId("some monsterId")
                .description("some description")
                .score(5)
                .status(OPEN)
                .build()));

        //When
        try {
            taskService.remove(taskId, "some otherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"remove\" method should throw forbidden when Task object with Status DONE should be removed")
    void removeForbiddenStatusDone() {
        //Given
        String taskId = "randomId";

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(Task.builder()
                .id(taskId)
                .userId("some userId")
                .monsterId("some monsterId")
                .description("some description")
                .score(5)
                .status(DONE)
                .build()));

        //When
        try {
            taskService.remove(taskId, "some userId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"remove\" method should throw not found when id not found")
    void removeNotFound() {
        //Given
        String taskId = "randomId";

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.empty());

        //When
        try {
            taskService.remove(taskId, "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    @DisplayName("The \"updateStatus\" method should return the updated Task object")
    void updateStatusInDone() {
        //Given
        String taskId = "randomId";
        Instant expectedTime = Instant.parse("2020-10-26T10:00:00Z");

        Task task = Task.builder()
                .id(taskId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(10)
                .status(OPEN)
                .build();

        Task updatedTask = Task.builder()
                .id(taskId)
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
        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(task));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));
        when(taskMongoDao.save(updatedTask)).thenReturn(updatedTask);

        //When
        Task result = taskService.updateStatus(taskId, "someMonsterId", "someUserId");

        //Then
        assertThat(result, is(updatedTask));
        verify(taskMongoDao).save(updatedTask);
    }

    @Test
    @DisplayName("The \"updateStatus\" method should return the updated Task object")
    void updateStatusInOpen() {
        //Given
        String taskId = "randomId";
        Instant savedTime = Instant.parse("2020-10-26T10:00:00Z");

        Task task = Task.builder()
                .id(taskId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("someDescription")
                .score(10)
                .status(DONE)
                .timestampOfDone(savedTime)
                .build();

        Task updatedTask = Task.builder()
                .id(taskId)
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

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(task));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));
        when(taskMongoDao.save(updatedTask)).thenReturn(updatedTask);

        //When
        Task result = taskService.updateStatus(taskId, "someMonsterId", "someUserId");

        //Then
        assertThat(result, is(updatedTask));
        verify(taskMongoDao).save(updatedTask);
    }

    @Test
    @DisplayName("The \"updateStatus\" method should save the updated Monster object")
    void updateStatusInDoneMonster() {
        //Given
        String taskId = "randomId";

        Task task = Task.builder()
                .id(taskId)
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
                .balance(0)
                .build();

        Monster updatedMonster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .name("someName")
                .image("someImage")
                .scoreDoneTasks(10)
                .payoutDoneRewards(0)
                .balance(0)
                .build();

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(task));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));
        when(monsterMongoDao.save(updatedMonster)).thenReturn(updatedMonster);

        //When
        taskService.updateStatus(taskId, "someMonsterId", "someUserId");

        //Then
        verify(monsterMongoDao).save(updatedMonster);
    }

    @Test
    @DisplayName("The \"updateStatus\" method should save the updated Monster object")
    void updateStatusInOpenMonster() {
        //Given
        String taskId = "randomId";

        Task task = Task.builder()
                .id(taskId)
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
                .scoreDoneTasks(25)
                .payoutDoneRewards(0)
                .balance(0)
                .build();

        Monster updatedMonster = Monster.builder()
                .id("someMonsterId")
                .userId("someUserId")
                .name("someName")
                .image("someImage")
                .scoreDoneTasks(15)
                .payoutDoneRewards(0)
                .balance(0)
                .build();

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(task));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));
        when(monsterMongoDao.save(updatedMonster)).thenReturn(updatedMonster);

        //When
        taskService.updateStatus(taskId, "someMonsterId", "someUserId");

        //Then
        verify(monsterMongoDao).save(updatedMonster);
    }

    @Test
    @DisplayName("The \"updateStatus\" method should throw forbidden when user with not matching userId try to modify a Task object")
    void updateStatusForbiddenUserId() {
        //Given
        String taskId = "randomId";

        Task task = Task.builder()
                .id(taskId)
                .userId("someUserId")
                .monsterId("someMonsterId")
                .description("some description")
                .score(10)
                .status(OPEN)
                .build();

        Monster monster = Monster.builder()
                .id("someMonsterId")
                .build();

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.of(task));
        when(monsterMongoDao.findById("someMonsterId")).thenReturn(Optional.of(monster));

        //When
        try {
            taskService.updateStatus(taskId, "someMonsterId", "some otherUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.FORBIDDEN));
        }
    }

    @Test
    @DisplayName("The \"updateStatus\" method should throw not found when taskId not found")
    void updateStatusTaskIdNotFound() {
        //Given
        String taskId = "randomId";

        when(taskMongoDao.findById(taskId)).thenReturn(Optional.empty());

        //When
        try {
            taskService.updateStatus(taskId, "someMonsterId", "someUserId");
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
            taskService.updateStatus(taskId, "someMonsterId", "someUserId");
            fail("missing exception");
        } catch (ResponseStatusException exception) {
            assertThat(exception.getStatus(), is(HttpStatus.NOT_FOUND));
        }
    }
}