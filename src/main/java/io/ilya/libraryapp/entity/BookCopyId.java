package io.ilya.libraryapp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BookCopyId implements Serializable {
    @GeneratedValue
    @Column(name = "copy_id")
    private Long id;
    @Column(name = "isbn")
    private String ISBN;
}
