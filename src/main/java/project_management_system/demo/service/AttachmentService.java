package project_management_system.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project_management_system.demo.dto.CreateAttachmentRequestDTO;
import project_management_system.demo.dto.CreateAttachmentResponseDTO;
import project_management_system.demo.dto.CreateCommentResponseDTO;
import project_management_system.demo.entity.Attachment;
import project_management_system.demo.entity.Comment;
import project_management_system.demo.entity.Task;
import project_management_system.demo.exception.NotFoundException;
import project_management_system.demo.repository.AttachmentRepository;
import project_management_system.demo.repository.TaskRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, TaskRepository taskRepository) {
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
    }
    public CreateAttachmentResponseDTO addAttachment(CreateAttachmentRequestDTO dto, UUID actingUserId, UUID taskId){
        Attachment attachment = new Attachment();
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found with this id: " + taskId));
        attachment.setUrl(dto.getUrl());
        attachment.setTask(task);

        attachmentRepository.save(attachment);
        return CreateAttachmentResponseDTO.builder()
                .id(attachment.getId())
                .url(attachment.getUrl())
                .taskId(task.getId())
                .build();
    }

    public Page<CreateAttachmentResponseDTO> listAttachmmentsByTask(UUID taskId, Pageable pageable){
        Page<Attachment> page = attachmentRepository.findByTask_Id(taskId, pageable);
        return page.map(this::mapToResponse);
    }

    public void deleteAttachment(UUID attachmentId, UUID actingUserId){
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(() -> new NotFoundException("Attachment not found with this id: " + attachmentId));
        attachmentRepository.delete(attachment);
    }



    private CreateAttachmentResponseDTO mapToResponse(Attachment attachment){
        return CreateAttachmentResponseDTO.builder()
                .id(attachment.getId())
                .url(attachment.getUrl())
                .taskId(attachment.getTask().getId())
                .build();
    }
}


