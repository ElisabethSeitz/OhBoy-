package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import de.neuefische.finalproject.ohboy.utils.TimestampUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static de.neuefische.finalproject.ohboy.model.Status.DONE;
import static de.neuefische.finalproject.ohboy.model.Status.OPEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    //Given
    final IdUtils idUtils = mock(IdUtils.class);
    final TimestampUtils timestampUtils = mock(TimestampUtils.class);
    final TaskMongoDao taskMongoDao = mock(TaskMongoDao.class);

    final TaskService taskService = new TaskService(taskMongoDao);

    final List<Task> tasks = new ArrayList<>(List.of(
            new Task("someId", "someMonsterId", "someDescription", 5, DONE, Instant.parse("1970-01-01T00:00:00Z")),
            new Task("someId2", "someMonsterId2", "someDescription2", 10, OPEN, Instant.parse("1970-01-01T00:00:00Z")),
            new Task("someId3", "someMonsterId3", "someDescription3", 15, OPEN, Instant.parse("1970-01-01T00:00:00Z"))
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

}