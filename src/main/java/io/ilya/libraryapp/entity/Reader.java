package io.ilya.libraryapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "readers")
@Data
@NoArgsConstructor
public class Reader {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String address;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @OneToMany(mappedBy = "readerId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<BookBorrow> borrowedBooks = new HashSet<>();

    public Reader(String name, String surname, String address, Date dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
}
