package project_management_system.demo.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_management_system.demo.dto.*;
import project_management_system.demo.entity.Project;
import project_management_system.demo.entity.Task;
import project_management_system.demo.service.TaskService;
import project_management_system.demo.utils.PageMapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<CreateTaskResponseDTO> createTask(@PathVariable UUID projectId, @Valid @RequestBody CreateTaskRequestDTO createTaskRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(createTaskRequestDTO, projectId));
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<PageResponseDTO<CreateTaskResponseDTO>> getTasksByProject(@PathVariable UUID projectId, Pageable pageable){
        Page<CreateTaskResponseDTO> taskPage = taskService.getTaskByProjectId(projectId,pageable).map(this::mapToResponse);
        PageResponseDTO<CreateTaskResponseDTO> response = PageMapper.toResponse(taskPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<PageResponseDTO<CreateTaskResponseDTO>> getTasksByUser(@PathVariable UUID userId, Pageable pageable){
        Page<CreateTaskResponseDTO> taskPage = taskService.getTaskByAssignedUser(userId, pageable).map(this::mapToResponse);
        PageResponseDTO<CreateTaskResponseDTO> response = PageMapper.toResponse(taskPage);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/tasks/{taskId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID taskId, @RequestBody UpdateStatusRequestDTO dto){
        taskService.updateStatus(taskId,dto.getStatus(), dto.getActorUserId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/tasks/{taskId}/assignee/{userId}")
    public ResponseEntity<Void> assignTask(@PathVariable UUID taskId, @PathVariable UUID userId){
        taskService.assignedTaskToUser(taskId,userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    private CreateTaskResponseDTO mapToResponse(Task task){
        return CreateTaskResponseDTO.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .projectId(task.getProject() != null ? task.getProject().getId() : null)
                .assignedToId(task.getAssignTo() != null ? task.getAssignTo().getId() : null)
                .createdAt(task.getCreatedAt())
                .build();
    }
}
