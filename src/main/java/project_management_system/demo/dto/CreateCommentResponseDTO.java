package project_management_system.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentResponseDTO {
    private UUID id;
    private UUID taskId;
    private UUID authorId;
}
