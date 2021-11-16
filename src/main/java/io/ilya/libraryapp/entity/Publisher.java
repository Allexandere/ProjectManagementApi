package io.ilya.libraryapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "publishers")
@Data
@NoArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String address;
    @OneToMany(mappedBy = "publisherId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Book> publishedBooks = new HashSet<>();

    public Publisher(String name, String address, Long parentId) {
        this.name = name;
        this.address = address;
    }
}
