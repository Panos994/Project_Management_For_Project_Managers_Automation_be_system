package project_management_system.demo.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_management_system.demo.dto.CreateProjectRequestDTO;
import project_management_system.demo.dto.CreateProjectResponseDTO;
import project_management_system.demo.dto.PageResponseDTO;
import project_management_system.demo.entity.Project;
import project_management_system.demo.service.ProjectService;
import project_management_system.demo.utils.PageMapper;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<CreateProjectResponseDTO> createProject(@Valid @RequestBody CreateProjectRequestDTO createProjectRequestDTO, @PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(createProjectRequestDTO, userId));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<PageResponseDTO<CreateProjectResponseDTO>> listByOwner(@PathVariable UUID ownerId, Pageable pageable) {
        Page<CreateProjectResponseDTO> projectPage = projectService.listProjectsByOwner(ownerId, pageable).map(this::mapToResponse);
        PageResponseDTO<CreateProjectResponseDTO> response = PageMapper.toResponse(projectPage);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<CreateProjectResponseDTO> getProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(mapToResponse(projectService.getProjectDetails(projectId)));
    }

    @PostMapping("/member/{memberId}/add")
    public ResponseEntity<CreateProjectResponseDTO> addMemberToProject(@PathVariable UUID memberId, @RequestParam UUID projectId) {
        return ResponseEntity.ok(mapToResponse((projectService.addMemberToProject(projectId, memberId))));
    }

    @DeleteMapping("/member/{memberId}/remove")
    public ResponseEntity<Void> removeMemberToProject(@PathVariable UUID memberId, @RequestParam UUID projectId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members/{userId}")
    public ResponseEntity<List<CreateProjectResponseDTO>> listProjectsWhereUserIsMember(@PathVariable UUID userId) {
        List<CreateProjectResponseDTO> listProjects = projectService.listProjectsWhereUserIsMember(userId).stream().map(this::mapToResponse).toList();
        return ResponseEntity.ok(listProjects);
    }


    private CreateProjectResponseDTO mapToResponse(Project project) {
        return CreateProjectResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .build();
    }
}
