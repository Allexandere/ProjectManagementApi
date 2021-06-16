package io.ilya.epamjuniortask.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
public class Project {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String title;

    @OneToMany(mappedBy = "enrolledProject", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<User> enrolledUsers = new HashSet<>();

    public Project(String title) {
        this.title = title;
    }

    public Project() {
    }
}
