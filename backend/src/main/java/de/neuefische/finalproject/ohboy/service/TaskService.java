package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddTaskDto;
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
import java.util.Optional;

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
}
