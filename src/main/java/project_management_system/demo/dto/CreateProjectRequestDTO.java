package project_management_system.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectRequestDTO {
    private String name;
    private String description;
}
