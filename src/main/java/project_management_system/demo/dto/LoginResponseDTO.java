package project_management_system.demo.dto;

import lombok.*;
import project_management_system.demo.entity.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private Role role;
}
