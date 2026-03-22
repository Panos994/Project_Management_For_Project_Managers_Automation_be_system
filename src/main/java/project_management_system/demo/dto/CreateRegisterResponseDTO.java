package project_management_system.demo.dto;

import lombok.*;
import project_management_system.demo.entity.Role;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRegisterResponseDTO {
    private UUID id;
    private String email;
    private Role role;
}
