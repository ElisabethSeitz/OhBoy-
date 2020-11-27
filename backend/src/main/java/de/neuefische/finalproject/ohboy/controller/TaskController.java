package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dto.AddTaskDto;
import de.neuefische.finalproject.ohboy.dto.UpdateTaskDto;
import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PutMapping("{monsterId}/tasks/{taskId}")
    public Task update(@RequestBody UpdateTaskDto updatedTask, @PathVariable String taskId, Principal principal) {
        if(!taskId.equals(updatedTask.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return taskService.update(updatedTask, principal.getName());
    }

    @DeleteMapping("{monsterId}/tasks/{taskId}")
    public void remove(@PathVariable String taskId, Principal principal) {
        taskService.remove(taskId, principal.getName());
    }
}
