package project_management_system.demo.dto;

import lombok.*;
import project_management_system.demo.entity.Role;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleRequestDTO {
    private UUID userId;
    private Role role;
}
