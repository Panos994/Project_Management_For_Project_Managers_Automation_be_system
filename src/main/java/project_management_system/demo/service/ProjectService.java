package project_management_system.demo.service;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project_management_system.demo.dto.CreateProjectRequestDTO;
import project_management_system.demo.dto.CreateProjectResponseDTO;
import project_management_system.demo.entity.Project;
import project_management_system.demo.entity.User;
import project_management_system.demo.exception.ForbiddenException;
import project_management_system.demo.exception.NotFoundException;
import project_management_system.demo.repository.ProjectRepository;
import project_management_system.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public CreateProjectResponseDTO createProject(CreateProjectRequestDTO createProjectRequestDTO, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with this id: " + userId));
        Project project = new Project();
        project.setName(createProjectRequestDTO.getName());
        project.setDescription(createProjectRequestDTO.getDescription());
        project.setOwner(user);
        project.setMembers(new ArrayList<>()); //instead of List.of in order to be mutable and add members later
        project.getMembers().add(user);
        projectRepository.save(project);
        return CreateProjectResponseDTO.builder()
                .id(project.getId())
                .description(project.getDescription())
                .name(project.getName())
                .build();
    }
    public Page<Project> listProjectsByOwner(UUID userId, Pageable pageable) {
        return projectRepository.findByOwner_Id(userId, pageable);
    }
    @Transactional
    public Project addMemberToProject(UUID projectId, UUID userId){
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found with this id: " + projectId));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with this id: " + userId));
        if(project.getMembers().stream().anyMatch(u -> u.getId().equals(userId))){
            throw new IllegalArgumentException("User is already a member of this project!");
        }
        project.getMembers().add(user);
        return projectRepository.save(project);
    }

    @Transactional
    public Project removeMemberFromProject(UUID projectId, UUID userId){
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found with this id: " + projectId));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with this id: " + userId));
        if(project.getOwner().equals(user)){
            throw new ForbiddenException("Project owner cannot be removed from the project!");
        }
        if(project.getMembers().stream().noneMatch(u -> u.getId().equals(userId))){
            throw new ForbiddenException("User is not a member of this project!");
        }
        project.getMembers().removeIf(u -> u.getId().equals(userId));
        return projectRepository.save(project);
    }

    public List<Project> listProjectsWhereUserIsMember(UUID userId){
        return projectRepository.findByMembers_Id(userId);
    }
    public Project getProjectDetails(UUID projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException("Project not found with this id: " + projectId));
        project.getMembers().size(); // to load members since it's a lazy relationship
        return project;
    }
}
