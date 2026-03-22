package project_management_system.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_management_system.demo.dto.CreateCommentRequestDTO;
import project_management_system.demo.dto.CreateCommentResponseDTO;
import project_management_system.demo.dto.PageResponseDTO;
import project_management_system.demo.dto.UpdateCommentRequestDTO;
import project_management_system.demo.entity.Comment;
import project_management_system.demo.service.CommentService;
import project_management_system.demo.utils.PageMapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponseDTO> addComment(@RequestBody CreateCommentRequestDTO request, @RequestParam UUID authorId, @RequestParam UUID taskId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(request, authorId, taskId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CreateCommentResponseDTO> getComment(@PathVariable UUID commentId){
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<PageResponseDTO<CreateCommentResponseDTO>> listCommentsByTask(@PathVariable UUID taskId, Pageable pageable){
        Page<CreateCommentResponseDTO> page = commentService.listCommentsByTask(taskId, pageable);
        PageResponseDTO<CreateCommentResponseDTO> res = PageMapper.toResponse(page);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CreateCommentResponseDTO> updateComment(@PathVariable UUID commentId, @RequestBody UpdateCommentRequestDTO dto, @RequestParam UUID actingUserId){
        CreateCommentResponseDTO updatedComment = commentService.updateComment(commentId, dto, actingUserId);
        return ResponseEntity.ok(updatedComment);

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId, @RequestParam UUID actingUserId){
        commentService.deleteComment(commentId, actingUserId);
        return ResponseEntity.noContent().build();

    }


//not in use at the moment - redundant need to remove it
    private CreateCommentResponseDTO mapToResponse(Comment comment){
        return CreateCommentResponseDTO.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor().getId())
                .taskId(comment.getTask().getId())
                .build();
    }
}
