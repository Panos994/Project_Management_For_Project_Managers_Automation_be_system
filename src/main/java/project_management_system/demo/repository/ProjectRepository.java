package project_management_system.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_management_system.demo.entity.Project;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByOwner_Id(UUID userId);
    List<Project> findByMembers_Id(UUID userId);
    Page<Project> findByOwner_Id(UUID userId, Pageable pageable);
}
