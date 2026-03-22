package project_management_system.demo.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequestDTO {
    private String text;
    private UUID authorId;
    private UUID taskId;
}
