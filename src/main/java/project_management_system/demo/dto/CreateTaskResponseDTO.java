package project_management_system.demo.dto;

import lombok.*;
import project_management_system.demo.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTaskResponseDTO {
    private UUID taskId;
    private String title;
    private UUID assignedToId;
    private TaskStatus status;
    private UUID projectId;
    private LocalDateTime createdAt;
}
