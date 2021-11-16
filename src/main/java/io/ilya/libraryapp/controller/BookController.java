package io.ilya.libraryapp.controller;

import io.ilya.libraryapp.dto.BookCreationRequest;
import io.ilya.libraryapp.entity.Book;
import io.ilya.libraryapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(@Autowired BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/create")
    public Book createBook(@RequestBody BookCreationRequest request) {
        return bookRepository.save(new Book(request.getTitle(),
                request.getAuthor(),
                request.getYear(),
                request.getPagesAmount(),
                request.getPublisherId()));
    }

    @GetMapping("/read")
    public List<Book> getBooks(@RequestParam(name = "id", required = false) Long bookId) {
        if (bookId == null) {
            return (List<Book>) bookRepository.findAll();
        } else {
            return List.of(bookRepository.findById(bookId).orElse(null));
        }
    }

    @PutMapping("/update")
    public Book updateBook(@PathVariable(name = "id") Long bookId,
                           @RequestBody BookCreationRequest updateBookRequest) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return null;
        }
        if (updateBookRequest.getTitle() != null) {
            book.setTitle(updateBookRequest.getTitle());
        }
        if (updateBookRequest.getAuthor() != null) {
            book.setAuthor(updateBookRequest.getAuthor());
        }
        if (updateBookRequest.getYear() != null) {
            book.setYear(updateBookRequest.getYear());
        }
        if (updateBookRequest.getPagesAmount() != null) {
            book.setPagesAmount(updateBookRequest.getPagesAmount());
        }
        if (updateBookRequest.getPublisherId() != null) {
            book.setPublisherId(updateBookRequest.getPublisherId());
        }
        return bookRepository.save(book);
    }

    @DeleteMapping("/delete")
    public Book deleteBook(@RequestParam(name = "id") Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return null;
        }
        bookRepository.deleteById(bookId);
        return book;
    }
}
