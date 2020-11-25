package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddTaskDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskMongoDao taskMongoDao;
    private final IdUtils idUtils;

    @Autowired
    public TaskService(TaskMongoDao taskMongoDao, IdUtils idUtils) {
        this.taskMongoDao = taskMongoDao;
        this.idUtils = idUtils;
    }

    public List<Task> findAllByMonsterId(String monsterId) {
        return taskMongoDao.findAllByMonsterId(monsterId);
    }

    public Task add(AddTaskDto dto, String monsterId, String userId) {
        Task taskToBeSaved = Task.builder()
                .id(idUtils.generateId())
                .userId(userId)
                .monsterId(monsterId)
                .description(dto.getDescription())
                .score(dto.getScore())
                .build();
        return taskMongoDao.save(taskToBeSaved);
    }
}
