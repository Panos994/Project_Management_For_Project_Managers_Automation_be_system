package project_management_system.demo.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailsDTO {
    private String name;
    private String description;
    private UUID ownerId;
    private List<UUID> memberIds;
}
