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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    private User owner;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="project_members",
    joinColumns = @JoinColumn(name="project_id"),inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> members;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Task> tasks;


    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
