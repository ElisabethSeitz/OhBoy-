package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddTaskDto;
import de.neuefische.finalproject.ohboy.dto.UpdateTaskDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Status;
import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import de.neuefische.finalproject.ohboy.utils.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskMongoDao taskMongoDao;
    private final IdUtils idUtils;
    private final TimestampUtils timestampUtils;
    private final MonsterMongoDao monsterMongoDao;

    @Autowired
    public TaskService(TaskMongoDao taskMongoDao, IdUtils idUtils, TimestampUtils timestampUtils, MonsterMongoDao monsterMongoDao) {
        this.taskMongoDao = taskMongoDao;
        this.idUtils = idUtils;
        this.timestampUtils = timestampUtils;
        this.monsterMongoDao = monsterMongoDao;
    }

    public List<Task> findAllByMonsterId(String monsterId) {
        return taskMongoDao.findAllByMonsterId(monsterId);
    }

    public Task add(AddTaskDto dto, String monsterId, String userId) {
        Monster monster = monsterMongoDao.findById(monsterId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (!Objects.equals(monster.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Task taskToBeSaved = Task.builder()
                .id(idUtils.generateId())
                .userId(userId)
                .monsterId(monsterId)
                .description(dto.getDescription())
                .score(dto.getScore())
                .status(Status.OPEN)
                .build();
        return taskMongoDao.save(taskToBeSaved);
    }

    public Task update(UpdateTaskDto update, String userId) {
        Task task = taskMongoDao.findById(update.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!Objects.equals(task.getUserId(), userId) || task.getStatus().equals(Status.DONE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Task updatedTask = Task.builder()
                .id(update.getId())
                .userId(userId)
                .monsterId(task.getMonsterId())
                .description(update.getDescription())
                .score(update.getScore())
                .status(task.getStatus())
                .build();

        return taskMongoDao.save(updatedTask);
    }

    public Task updateStatus(String taskId, String monsterId, String userId) {
        Task task = taskMongoDao.findById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Monster monster = monsterMongoDao.findById(monsterId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(task.getStatus().equals(Status.OPEN)){
            task.setStatus(Status.DONE);
            task.setTimestampOfDone(timestampUtils.generateTimeStampEpochSeconds());
            monster.setScoreDoneTasks(monster.getScoreDoneTasks() + task.getScore());
        } else {task.setStatus(Status.OPEN);
                task.setTimestampOfDone(null);
                monster.setScoreDoneTasks(monster.getScoreDoneTasks() - task.getScore());
        }
        monsterMongoDao.save(monster);

        if(!Objects.equals(task.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return taskMongoDao.save(task);
    }

    public void remove(String taskId, String userId) {
        Task task = taskMongoDao.findById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!Objects.equals(task.getUserId(), userId) || task.getStatus().equals(Status.DONE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        taskMongoDao.deleteById(taskId);
    }
}
