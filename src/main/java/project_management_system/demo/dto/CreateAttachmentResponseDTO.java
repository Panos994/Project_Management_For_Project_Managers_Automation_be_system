package project_management_system.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAttachmentResponseDTO {
    private UUID id;
    private String url;
    private UUID taskId;
}
