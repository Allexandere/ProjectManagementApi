package io.ilya.epamjuniortask.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "enrolledUser", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Task> enrolledTasks = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enrolled_project_id", referencedColumnName = "id")
    private Project enrolledProject;

    public User(String name) {
        this.name = name;
    }

    public User(){}

    public void assignProject(Project project){
        this.enrolledProject = project;
    }
}
