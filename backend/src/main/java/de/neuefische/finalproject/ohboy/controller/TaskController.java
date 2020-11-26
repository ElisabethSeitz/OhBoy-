package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.dto.AddTaskDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/monster/")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{monsterId}/tasks")
    public List<Task> getAllByMonsterId(@PathVariable String monsterId) {
        return taskService.findAllByMonsterId(monsterId);
    }

    @PostMapping("{monsterId}/tasks")
    public Task add(@RequestBody AddTaskDto dto, @PathVariable String monsterId, Principal principal){
        return this.taskService.add(dto, monsterId, principal.getName());
    }
}
