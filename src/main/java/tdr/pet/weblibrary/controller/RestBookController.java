package tdr.pet.weblibrary.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.BookMapper;
import tdr.pet.weblibrary.service.AuthorService;
import tdr.pet.weblibrary.service.BookService;
import tdr.pet.weblibrary.service.PublisherService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/book")
public class RestBookController {
    private final BookService bookService;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final BookMapper bookMapper;

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookService.findBooksByIsbn(isbn);
        return ResponseEntity.ok(bookMapper.toDTO(book));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Publisher publisher = publisherService.findPublisherByName(bookDTO.getPublisherDTO().getName());
        Set<Author> authors = bookDTO.getAuthors().stream()
                .map(authorDTO -> authorService.findAuthorByName(authorDTO.getEmail()))
                .collect(Collectors.toSet());

        Book book = bookMapper.toEntity(bookDTO);
        book.setPublisher(publisher);
        book.setAuthors(authors);

        bookService.createBook(book);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDTO> updateBookByIsbn(@PathVariable String isbn, @Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Publisher publisher = publisherService.findPublisherByName(bookDTO.getPublisherDTO().getName());
        Set<Author> authors = bookDTO.getAuthors().stream()
                .map(authorDTO -> authorService.findAuthorByName(authorDTO.getEmail()))
                .collect(Collectors.toSet());

        Book book = bookMapper.toEntity(bookDTO);
        book.setPublisher(publisher);
        book.setAuthors(authors);

        bookService.updateBookByIsbn(isbn, book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBookByIsbn(@PathVariable String isbn) {
        bookService.deleteBookByISBN(isbn);
        return ResponseEntity.ok().build();
    }
}