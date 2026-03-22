package project_management_system.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project_management_system.demo.dto.CreateAttachmentRequestDTO;
import project_management_system.demo.dto.CreateAttachmentResponseDTO;
import project_management_system.demo.dto.PageResponseDTO;
import project_management_system.demo.entity.Attachment;
import project_management_system.demo.service.AttachmentService;
import project_management_system.demo.utils.PageMapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity<CreateAttachmentResponseDTO> addAttachment(@RequestBody CreateAttachmentRequestDTO dto, @RequestParam UUID actingUserId, @RequestParam UUID taskId){
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.addAttachment(dto, actingUserId, taskId));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<PageResponseDTO<CreateAttachmentResponseDTO>> listAttachmentsByTask(@PathVariable UUID taskId, Pageable pageable){
        Page<CreateAttachmentResponseDTO> page = attachmentService.listAttachmmentsByTask(taskId, pageable);
        PageResponseDTO<CreateAttachmentResponseDTO> res = PageMapper.toResponse(page);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable UUID attachmentId, @RequestParam UUID actingUserId){
        attachmentService.deleteAttachment(attachmentId, actingUserId);
        return ResponseEntity.noContent().build();
    }
}
