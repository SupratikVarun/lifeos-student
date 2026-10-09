package com.lifeos.student.service;

import com.lifeos.student.dto.CreateTaskDTO;
import com.lifeos.student.dto.TaskResponseDTO;
import com.lifeos.student.dto.UpdateTaskDTO;
import com.lifeos.student.entity.Task;
import com.lifeos.student.entity.User;
import com.lifeos.student.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository,
                       UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    // CREATE TASK
    public TaskResponseDTO createTask(CreateTaskDTO request, String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .priority(request.getPriority())
                .status(request.getStatus())
                .user(user)
                .build();

        taskRepository.save(task);

        return mapToDTO(task);
    }

    // GET ALL TASKS
    public List<TaskResponseDTO> getAllTasks(String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        return taskRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET TASK BY ID
    public TaskResponseDTO getTaskById(UUID taskId, String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return mapToDTO(task);
    }

    // UPDATE TASK
    public TaskResponseDTO updateTask(UUID taskId,
                                      UpdateTaskDTO request,
                                      String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());

        taskRepository.save(task);

        return mapToDTO(task);
    }

    // DELETE TASK
    public void deleteTask(UUID taskId, String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        taskRepository.delete(task);
    }

    // DTO Mapper
    private TaskResponseDTO mapToDTO(Task task) {

        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .build();
    }
}