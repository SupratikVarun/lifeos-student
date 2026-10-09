package com.lifeos.student.controller;

import com.lifeos.student.dto.CreateTaskDTO;
import com.lifeos.student.dto.TaskResponseDTO;
import com.lifeos.student.dto.UpdateTaskDTO;
import com.lifeos.student.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE TASK
    @PostMapping
    public TaskResponseDTO createTask(
            @RequestBody CreateTaskDTO request,
            Authentication authentication) {

        return taskService.createTask(request, authentication.getName());
    }

    // GET ALL TASKS
    @GetMapping
    public List<TaskResponseDTO> getAllTasks(Authentication authentication) {

        return taskService.getAllTasks(authentication.getName());
    }

    // GET TASK BY ID
    @GetMapping("/{taskId}")
    public TaskResponseDTO getTaskById(
            @PathVariable UUID taskId,
            Authentication authentication) {

        return taskService.getTaskById(taskId, authentication.getName());
    }

    // UPDATE TASK
    @PutMapping("/{taskId}")
    public TaskResponseDTO updateTask(
            @PathVariable UUID taskId,
            @RequestBody UpdateTaskDTO request,
            Authentication authentication) {

        return taskService.updateTask(taskId, request, authentication.getName());
    }

    // DELETE TASK
    @DeleteMapping("/{taskId}")
    public String deleteTask(
            @PathVariable UUID taskId,
            Authentication authentication) {

        taskService.deleteTask(taskId, authentication.getName());

        return "Task deleted successfully.";
    }
}