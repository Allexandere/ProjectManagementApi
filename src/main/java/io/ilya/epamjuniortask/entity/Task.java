package io.ilya.epamjuniortask.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enrolled_user_id", referencedColumnName = "id")
    private User enrolledUser;

    @JsonIgnore
    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Task> subTasks = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_task_id", referencedColumnName = "id")
    private Task parentTask;

    public Task(String title){
        this.title = title;
    }

    public Task(String title, User enrolledUser){
        this.title = title;
        this.enrolledUser = enrolledUser;
    }

    public Task(){}

    public void assignParentTask(Task parentTask){
        this.parentTask = parentTask;
    }

    public void assignEnrolledUser(User user){
        this.enrolledUser = user;
    }
}
