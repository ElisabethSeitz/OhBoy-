package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monster/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{monsterId}")
    public List<Task> getByMonsterId(@PathVariable String monsterId) {
        return taskService.findAllByMonsterId(monsterId);
    }
}
