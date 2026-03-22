package project_management_system.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotBlank
    @Column
    private String password;

    @Column
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "assignTo", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Task> tasks;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments;


    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }



}
