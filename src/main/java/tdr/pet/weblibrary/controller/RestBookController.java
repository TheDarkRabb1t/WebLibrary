package tdr.pet.weblibrary.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.AuthorMapper;
import tdr.pet.weblibrary.model.mapper.BookMapper;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.service.AuthorService;
import tdr.pet.weblibrary.service.BookService;
import tdr.pet.weblibrary.service.PublisherService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/book")
public class RestBookController {
    private final BookService bookService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final PublisherMapper publisherMapper;

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDTO>> findBooksByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.findBooksByTitle(title).stream().map(bookMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Set<BookDTO>> findBooksByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findBooksByIsbn(isbn).stream().map(bookMapper::toDTO).collect(Collectors.toSet()));
    }

    @PostMapping("/authors")
    public ResponseEntity<List<BookDTO>> findBooksByAuthors(@RequestBody Set<AuthorDTO> authorDTOs) {
        Set<Author> authors = authorDTOs.stream()
                .map(authorMapper::toEntity)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(bookService.findBooksByAuthors(authors).stream()
                .map(bookMapper::toDTO)
                .toList());
    }

    @PostMapping("/publisher")
    public ResponseEntity<List<BookDTO>> findBooksByPublisher(@RequestBody PublisherDTO publisherDTO) {
        Publisher publisher = publisherMapper.toEntity(publisherDTO);
        return ResponseEntity.ok(bookService.findBooksByPublishers(publisher).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{isbn}/authors")
    public ResponseEntity<List<AuthorDTO>> findAuthorsByBookIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findAuthorsByBookIsbn(isbn).stream().map(authorMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{isbn}/publisher")
    public ResponseEntity<PublisherDTO> findPublisherByBookIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(publisherMapper.toDTO(bookService.findPublisherByBookIsbn(isbn)));
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody @Valid BookDTO bookDTO) {
        bookService.createBook(bookMapper.toEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBookById(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        bookService.updateBookById(id, bookDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/isbn/{isbn}")
    public ResponseEntity<Void> updateBookByIsbn(@PathVariable String isbn, @RequestBody @Valid BookDTO bookDTO) {
        bookService.updateBookByIsbn(isbn, bookDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/isbn/{isbn}")
    public ResponseEntity<Void> deleteBookByIsbn(@PathVariable String isbn) {
        bookService.deleteBookByISBN(isbn);
        return ResponseEntity.noContent().build();
    }
}