package io.ilya.libraryapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class    Book {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private Integer year;
    @Column(name = "pages_amount")
    private Integer pagesAmount;
    @Column(name = "publisher_id")
    private Long publisherId;
    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
    @OneToMany(mappedBy = "originalId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<BookCopy> copies = new HashSet<>();

    public Book(String title, String author, Integer year, Integer pagesAmount, Long publisherId) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.pagesAmount = pagesAmount;
        this.publisherId = publisherId;
    }
}
