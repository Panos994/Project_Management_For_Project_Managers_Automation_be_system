package project_management_system.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project_management_system.demo.dto.CreateCommentRequestDTO;
import project_management_system.demo.dto.CreateCommentResponseDTO;
import project_management_system.demo.dto.UpdateCommentRequestDTO;
import project_management_system.demo.entity.Comment;
import project_management_system.demo.entity.Task;
import project_management_system.demo.entity.User;
import project_management_system.demo.exception.NotFoundException;
import project_management_system.demo.repository.CommentRepository;
import project_management_system.demo.repository.TaskRepository;
import project_management_system.demo.repository.UserRepository;

import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public CreateCommentResponseDTO addComment(CreateCommentRequestDTO dto, UUID authorId, UUID taskId){
        User author = userRepository.findById(authorId).orElseThrow(() -> new NotFoundException(("Author user not found with this id" + authorId)));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found with this id: " + taskId));

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setTask(task);
        comment.setText(dto.getText());

        commentRepository.save(comment);
        return CreateCommentResponseDTO.builder()
                .id(comment.getId())
                .authorId(author.getId())
                .taskId(task.getId())
                .build();

    }

    public Page<CreateCommentResponseDTO> listCommentsByTask(UUID taskId, Pageable pageable){
        Page<Comment> comment = commentRepository.findByTask_Id(taskId, pageable);
        return comment.map(this::mapToResponse);
    }

    public CreateCommentResponseDTO getComment(UUID commentId){
         Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found with this id: " + commentId));
        return CreateCommentResponseDTO.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor().getId())
                .taskId(comment.getTask().getId())
                .build();
    }

    public CreateCommentResponseDTO updateComment(UUID commentId, UpdateCommentRequestDTO dto, UUID actingUserId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found with this id: " + commentId));
        if(!comment.getAuthor().getId().equals(actingUserId)){
            throw new IllegalArgumentException("Only the author of the comment can update it!");
        }
        if(dto.getText() != null || dto.getText().isBlank()){
            throw new IllegalArgumentException("Comment text cannot be empty!");
        }
        comment.setText(dto.getText());
        commentRepository.save(comment);
        return CreateCommentResponseDTO.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor().getId())
                .taskId(comment.getTask().getId())
                .build();
    }
    public void deleteComment(UUID commentId, UUID actingUserId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found with this id: " + commentId));

        if(!comment.getAuthor().getId().equals(actingUserId)){
            throw new IllegalArgumentException("Only the author of the comment can delete it!");
        }
        commentRepository.delete(comment);
    }

    private CreateCommentResponseDTO mapToResponse(Comment comment){
        return CreateCommentResponseDTO.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor().getId())
                .taskId(comment.getTask().getId())
                .build();
    }
}
