package project_management_system.demo.dto;

import lombok.*;
import project_management_system.demo.entity.Priority;
import project_management_system.demo.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTaskRequestDTO {
    private String title;
    private TaskStatus status;
    private UUID assignedTo;
    private UUID projectId;
    private LocalDateTime dueDate;
    private Priority priority;
}
