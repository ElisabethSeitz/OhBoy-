package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.TaskMongoDao;
import de.neuefische.finalproject.ohboy.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskMongoDao taskMongoDao;

    @Autowired
    public TaskService(TaskMongoDao taskMongoDao) {
        this.taskMongoDao = taskMongoDao;
    }

    public List<Task> findByMonsterId(String monsterId) {
        return taskMongoDao.findAllByMonsterId(monsterId);
    }
}
