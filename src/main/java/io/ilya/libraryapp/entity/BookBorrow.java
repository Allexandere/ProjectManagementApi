package io.ilya.libraryapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book_borrows")
@Data
@NoArgsConstructor
public class BookBorrow {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "borrowed_book_id")
    private Long borrowedBookId;
    @Column(name = "reader_id")
    private Long readerId;
    @Column(name = "expiry_date")
    private Date expiryDate;

    public BookBorrow(Long borrowedBookId, Long readerId, Date expiryDate) {
        this.borrowedBookId = borrowedBookId;
        this.readerId = readerId;
        this.expiryDate = expiryDate;
    }
}
