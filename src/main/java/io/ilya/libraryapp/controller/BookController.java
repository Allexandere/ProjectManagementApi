package io.ilya.libraryapp.controller;

import com.github.javafaker.Faker;
import io.ilya.libraryapp.dto.BookCreationRequest;
import io.ilya.libraryapp.entity.*;
import io.ilya.libraryapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BookCopyRepository copyRepository;
    private final BookBorrowRepository borrowRepository;
    private final ReaderRepository readerRepository;

    private final Faker faker = new Faker();

    public BookController(@Autowired BookRepository bookRepository,
                          @Autowired PublisherRepository publisherRepository,
                          @Autowired BookCopyRepository copyRepository,
                          @Autowired BookBorrowRepository borrowRepository,
                          @Autowired ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.copyRepository = copyRepository;
        this.borrowRepository = borrowRepository;
        this.readerRepository = readerRepository;
    }

    @PostMapping("/fake-books")
    public List<Book> fakeBooks(@RequestParam Long amount) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Book book = new Book(faker.name().title(),
                    faker.name().fullName(),
                    faker.number().numberBetween(1900, 2021),
                    faker.number().numberBetween(50, 1200),
                    faker.number().numberBetween(1, publisherRepository.count()));
            books.add(book);
        }
        return (List<Book>)bookRepository.saveAll(books);
    }

    @PostMapping("/fake-publishers")
    public List<Publisher> fakePublishers(@RequestParam Long amount) {
        List<Publisher> publishers = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Publisher publisher = new Publisher(faker.name().title(),
                    faker.address().fullAddress());
            publishers.add(publisher);
        }
        return (List<Publisher>)publisherRepository.saveAll(publishers);
    }

    @PostMapping("/fake-copies")
    public List<BookCopy> fakeCopies(@RequestParam Long amount) {
        List<BookCopy> copies = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            BookCopy copy = new BookCopy(faker.letterify("???????-????????-??????"),
                    faker.address().zipCode(),
                    faker.number().numberBetween(1, bookRepository.count()));
            copies.add(copy);
        }
        return (List<BookCopy>)copyRepository.saveAll(copies);
    }

    @PostMapping("/fake-readers")
    public List<Reader> fakeReaders(@RequestParam Long amount) {
        List<Reader> readers = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Reader reader = new Reader(faker.name().firstName(),
                    faker.name().lastName(),
                    faker.address().fullAddress(),
                    faker.date().birthday());
            readers.add(reader);
        }
        return (List<Reader>)readerRepository.saveAll(readers);
    }

    @PostMapping("/fake-borrows")
    public List<BookBorrow> fakeBorrows(@RequestParam Long amount) {
        List<BookBorrow> borrows = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            BookBorrow borrow = new BookBorrow(faker.number().numberBetween(1, copyRepository.count()),
                    faker.number().numberBetween(1, readerRepository.count()),
                            faker.date().future(20, TimeUnit.DAYS));
            borrows.add(borrow);
        }
        return (List<BookBorrow>)borrowRepository.saveAll(borrows);
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
