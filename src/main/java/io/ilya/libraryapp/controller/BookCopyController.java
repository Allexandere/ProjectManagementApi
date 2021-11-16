package io.ilya.libraryapp.controller;

import io.ilya.libraryapp.dto.BookCopyCreationRequest;
import io.ilya.libraryapp.dto.BookCreationRequest;
import io.ilya.libraryapp.entity.Book;
import io.ilya.libraryapp.entity.BookCopy;
import io.ilya.libraryapp.entity.BookCopyId;
import io.ilya.libraryapp.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/copies")
public class BookCopyController {
    private final BookCopyRepository bookCopyRepository;

    public BookCopyController(@Autowired BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    @PostMapping("/create")
    public BookCopy createCopy(@RequestBody BookCopyCreationRequest request) {
        BookCopyId id = new BookCopyId();
        id.setISBN(request.getISBN());
        return bookCopyRepository.save(new BookCopy(id, request.getPosition(), request.getOriginalId()));
    }

    @GetMapping("/read")
    public List<BookCopy> getCopies(@RequestParam(name = "id", required = false) Long copyId,
                                   @RequestParam(name = "isbn", required = false) String ISBN) {
        if (copyId == null || ISBN == null) {
            return (List<BookCopy>) bookCopyRepository.findAll();
        } else {
            BookCopyId id = new BookCopyId();
            id.setId(copyId);
            id.setISBN(ISBN);
            return List.of(bookCopyRepository.findById(id).orElse(null));
        }
    }

    @PutMapping("/update")
    public BookCopy updateCopy(@RequestParam(name = "id", required = false) Long copyId,
                           @RequestParam(name = "isbn", required = false) String ISBN,
                           @RequestBody BookCopyCreationRequest updateCopyRequest) {
        BookCopyId id = new BookCopyId();
        id.setId(copyId);
        id.setISBN(ISBN);
        BookCopy copy = bookCopyRepository.findById(id).orElse(null);
        if (copy == null) {
            return null;
        }
        if (updateCopyRequest.getPosition() != null) {
            copy.setPosition(copy.getPosition());
        }
        return bookCopyRepository.save(copy);
    }

    @DeleteMapping("/delete")
    public BookCopy deleteCopy(@RequestParam(name = "id", required = false) Long copyId,
                               @RequestParam(name = "isbn", required = false) String ISBN) {
        BookCopyId id = new BookCopyId();
        id.setId(copyId);
        id.setISBN(ISBN);
        BookCopy copy = bookCopyRepository.findById(id).orElse(null);
        if (copy == null) {
            return null;
        }
        bookCopyRepository.deleteById(id);
        return copy;
    }
}
