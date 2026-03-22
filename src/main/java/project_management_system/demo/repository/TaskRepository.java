package project_management_system.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_management_system.demo.entity.Priority;
import project_management_system.demo.entity.Task;
import project_management_system.demo.entity.TaskStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findByAssignTo_Id(UUID userId, Pageable pageable);
    Page<Task> findByProject_Id(UUID projectId, Pageable pageable);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByPriority(Priority priority);
    List<Task> findByProject_IdAndAssignTo_Id(UUID projectId, UUID userId);
}
