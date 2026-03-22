package project_management_system.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRegisterRequestDTO {
    private String email;
    private String password;
}
