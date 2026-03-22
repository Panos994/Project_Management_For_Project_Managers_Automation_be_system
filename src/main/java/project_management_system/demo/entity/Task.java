package project_management_system.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    private User assignTo;

    @ManyToOne
    private Project project;

    @Column
    private LocalDateTime dueDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
