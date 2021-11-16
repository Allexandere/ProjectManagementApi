package io.ilya.libraryapp.repository;

import io.ilya.libraryapp.entity.BookCopy;
import io.ilya.libraryapp.entity.BookCopyId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends CrudRepository<BookCopy, BookCopyId> {
}
