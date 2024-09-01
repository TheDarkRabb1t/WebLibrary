package tdr.pet.weblibrary.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.mapper.BookMapper;
import tdr.pet.weblibrary.service.BookService;

@RestController("/book")
@AllArgsConstructor
public class RestBookController {
    private final BookMapper bookMapper;
    private final BookService bookService;

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@Valid @PathVariable String isbn, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookMapper.toDTO(bookService.getBookByISBN(isbn)));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createBook(@Valid BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        bookService.createBook(bookMapper.toEntity(bookDTO));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDTO> updateBookByIsbn(@Valid @PathVariable String isbn, @Valid BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        bookService.updateBookByIsbn(isbn, bookMapper.toEntity(bookDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBookByIsbn(@Valid @PathVariable String isbn, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        bookService.deleteBookByISBN(isbn);
        return ResponseEntity.ok().build();
    }
}
