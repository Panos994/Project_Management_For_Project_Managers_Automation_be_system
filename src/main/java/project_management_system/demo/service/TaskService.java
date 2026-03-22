package project_management_system.demo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project_management_system.demo.dto.CreateTaskRequestDTO;
import project_management_system.demo.dto.CreateTaskResponseDTO;
import project_management_system.demo.entity.Project;
import project_management_system.demo.entity.Task;
import project_management_system.demo.entity.TaskStatus;
import project_management_system.demo.entity.User;
import project_management_system.demo.exception.ForbiddenException;
import project_management_system.demo.exception.NotFoundException;
import project_management_system.demo.repository.ProjectRepository;
import project_management_system.demo.repository.TaskRepository;
import project_management_system.demo.repository.UserRepository;

import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public CreateTaskResponseDTO createTask(CreateTaskRequestDTO createTaskRequestDTO, UUID projectId) {
        UUID assignedUserId = createTaskRequestDTO.getAssignedTo();
        User assignedUser = userRepository.findById(assignedUserId).orElseThrow(()-> new NotFoundException("User not found with this id: " + assignedUserId));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found with this id: " + projectId));
        boolean isMember = project.getMembers().stream().anyMatch(u-> u.getId().equals(assignedUserId));
        if(!isMember){
            throw new ForbiddenException("User must be a project member to be assigned");
        }
        if(createTaskRequestDTO.getDueDate() != null && createTaskRequestDTO.getDueDate().isBefore(java.time.LocalDateTime.now())){
            throw new ForbiddenException("Due date must be in the future!");
        }
        Task task = new Task();
        task.setTitle(createTaskRequestDTO.getTitle());
        task.setStatus(createTaskRequestDTO.getStatus() != null ? createTaskRequestDTO.getStatus() : TaskStatus.TODO);
        task.setDueDate(createTaskRequestDTO.getDueDate());
        task.setProject(project);
        task.setPriority(createTaskRequestDTO.getPriority());
        task.setAssignTo(assignedUser);

        taskRepository.save(task);
        return CreateTaskResponseDTO.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .projectId(task.getProject() != null ? task.getProject().getId() : null)
                .assignedToId(assignedUser.getId())
                .createdAt(task.getCreatedAt())
                .build();
    }
    public Task getTaskById(UUID taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("task not found with this id: " + taskId));
    }
    public Page<Task> getTaskByProjectId(UUID projectId, Pageable pageable){
        return taskRepository.findByProject_Id(projectId,pageable);
    }
    public Page<Task> getTaskByAssignedUser(UUID userId, Pageable pageable){
        return taskRepository.findByAssignTo_Id(userId,pageable);
    }
    public Task updateStatus(UUID taskId, TaskStatus status, UUID actorUserId){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found with this id: " + taskId));
        User actor = userRepository.findById(actorUserId).orElseThrow(() -> new NotFoundException("User not found with this id: " + actorUserId));
        UUID assigneeId  = task.getAssignTo() != null ? task.getAssignTo().getId() : null;
        UUID ownerId = task.getProject().getOwner() != null ? task.getProject().getOwner().getId() : null;
        boolean isAssignee = assigneeId != null && assigneeId.equals(actorUserId);
        boolean isOwner = ownerId != null && ownerId.equals(actorUserId);
        if(!isAssignee && !isOwner){
            throw new ForbiddenException("Not allowed to update status!");
        }
        task.setStatus(status);
        return taskRepository.save(task);
    }
    public Task assignedTaskToUser(UUID userId, UUID taskId){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with this id: " + userId));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found with this id: " + taskId));
        boolean isMember = task.getProject().getMembers().stream().anyMatch(u-> u.getId().equals(userId));
        if(!isMember){
            throw new ForbiddenException("User must be a project memeber to be assigned to this task");
        }
        task.setAssignTo(user);
        return taskRepository.save(task);
    }
    public void deleteTask(UUID taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new NotFoundException("Task not found with"));
        taskRepository.delete(task);
    }
}
