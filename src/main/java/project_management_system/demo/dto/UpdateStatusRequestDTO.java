package project_management_system.demo.dto;

import lombok.*;
import project_management_system.demo.entity.TaskStatus;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStatusRequestDTO {
    private TaskStatus status;
    private UUID actorUserId;
}
