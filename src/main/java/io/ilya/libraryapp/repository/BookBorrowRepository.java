package io.ilya.libraryapp.repository;

import io.ilya.libraryapp.entity.BookBorrow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBorrowRepository extends CrudRepository<BookBorrow, Long> {
}
