package io.ilya.libraryapp.controller;

import io.ilya.libraryapp.dto.BookBorrowCreationRequest;
import io.ilya.libraryapp.dto.BookCreationRequest;
import io.ilya.libraryapp.entity.Book;
import io.ilya.libraryapp.entity.BookBorrow;
import io.ilya.libraryapp.repository.BookBorrowRepository;
import io.ilya.libraryapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrows")
public class BookBorrowController {
    private final BookBorrowRepository borrowRepository;

    public BookBorrowController(@Autowired BookBorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @PostMapping("/create")
    public BookBorrow createBorrow(@RequestBody BookBorrowCreationRequest request) {
        return borrowRepository.save(new BookBorrow(request.getBorrowerBookId(),
                request.getReaderId(),
                request.getExpiryDate()));
    }

    @GetMapping("/read")
    public List<BookBorrow> getBorrows(@RequestParam(name = "id", required = false) Long borrowId) {
        if (borrowId == null) {
            return (List<BookBorrow>) borrowRepository.findAll();
        } else {
            return List.of(borrowRepository.findById(borrowId).orElse(null));
        }
    }

    @PutMapping("/update")
    public BookBorrow updateBorrows(@PathVariable(name = "id") Long borrowId,
                           @RequestBody BookBorrowCreationRequest updateBorrowRequest) {
        BookBorrow borrow = borrowRepository.findById(borrowId).orElse(null);
        if (borrow == null) {
            return null;
        }
        if (updateBorrowRequest.getExpiryDate() != null) {
            borrow.setExpiryDate(updateBorrowRequest.getExpiryDate());
        }
        return borrowRepository.save(borrow);
    }

    @DeleteMapping("/delete")
    public BookBorrow deleteBorrow(@RequestParam(name = "id") Long borrowId) {
        BookBorrow borrow = borrowRepository.findById(borrowId).orElse(null);
        if (borrow == null) {
            return null;
        }
        borrowRepository.deleteById(borrowId);
        return borrow;
    }
}
