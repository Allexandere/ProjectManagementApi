package io.ilya.libraryapp.controller;

import io.ilya.libraryapp.dto.BookCopyCreationRequest;
import io.ilya.libraryapp.entity.BookCopy;
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
        return bookCopyRepository.save(new BookCopy(request.getISBN(), request.getPosition(), request.getOriginalId()));
    }

    @GetMapping("/read")
    public List<BookCopy> getCopies(@RequestParam(name = "id", required = false) Long copyId) {
        if (copyId == null) {
            return (List<BookCopy>) bookCopyRepository.findAll();
        } else {
            return List.of(bookCopyRepository.findById(copyId).orElse(null));
        }
    }

    @PutMapping("/update")
    public BookCopy updateCopy(@RequestParam(name = "id", required = false) Long copyId,
                           @RequestBody BookCopyCreationRequest updateCopyRequest) {
        BookCopy copy = bookCopyRepository.findById(copyId).orElse(null);
        if (copy == null) {
            return null;
        }
        if (updateCopyRequest.getPosition() != null) {
            copy.setPosition(copy.getPosition());
        }
        return bookCopyRepository.save(copy);
    }

    @DeleteMapping("/delete")
    public BookCopy deleteCopy(@RequestParam(name = "id", required = false) Long copyId) {
        BookCopy copy = bookCopyRepository.findById(copyId).orElse(null);
        if (copy == null) {
            return null;
        }
        bookCopyRepository.deleteById(copyId);
        return copy;
    }
}
