package io.ilya.libraryapp.repository;

import io.ilya.libraryapp.entity.BookCopy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {
}
