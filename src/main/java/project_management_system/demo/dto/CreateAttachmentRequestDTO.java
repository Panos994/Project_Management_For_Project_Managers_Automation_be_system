package project_management_system.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAttachmentRequestDTO {
    private String url;
    private UUID taskId;
}
