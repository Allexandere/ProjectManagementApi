package io.ilya.libraryapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(name = "parent_id")
    private Long parentId;
    @OneToMany(mappedBy = "parentId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Category> childCategories = new HashSet<>();

    public Category(String title) {
        this.title = title;
    }
}
