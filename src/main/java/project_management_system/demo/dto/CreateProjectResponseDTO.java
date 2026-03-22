package project_management_system.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectResponseDTO {
    private UUID id;
    private String name;
    private String description;
}
