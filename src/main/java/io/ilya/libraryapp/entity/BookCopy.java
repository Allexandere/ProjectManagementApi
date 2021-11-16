package io.ilya.libraryapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_copies")
@Data
@NoArgsConstructor
public class BookCopy {

    @EmbeddedId
    private BookCopyId id;
    @Column
    private String position;
    @Column(name = "original_id")
    private Long originalId;

    public BookCopy(BookCopyId id, String position, Long originalId) {
        this.id = id;
        this.position = position;
        this.originalId = originalId;
    }
}
