package io.ilya.libraryapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_copies")
@Data
@NoArgsConstructor
public class BookCopy {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "isbn")
    private String ISBN;
    @Column
    private String position;
    @Column(name = "original_id")
    private Long originalId;

    public BookCopy(String ISBN, String position, Long originalId) {
        this.ISBN = ISBN;
        this.position = position;
        this.originalId = originalId;
    }
}
