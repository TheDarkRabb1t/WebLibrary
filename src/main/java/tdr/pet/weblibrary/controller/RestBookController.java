package tdr.pet.weblibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.AuthorMapper;
import tdr.pet.weblibrary.model.mapper.BookMapper;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.service.BookService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/book")
@Tag(name = "Books Controller", description = "Endpoints for managing books")
public class RestBookController {

    private final BookService bookService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final PublisherMapper publisherMapper;

    @Operation(summary = "Get books", description = "Returns a list of books based on page values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
    })
    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<Page<BookDTO>> getBooks(@RequestParam Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(bookService.getBooks(PageRequest.of(pageNumber, pageSize)).map(bookMapper::toDTO));
    }

    @Operation(summary = "Find books by title", description = "Returns a list of books based on the provided title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid title supplied"),
            @ApiResponse(responseCode = "404", description = "Books not found")
    })
    @GetMapping(value = "/title/{title}", produces = "application/json")
    public ResponseEntity<List<BookDTO>> findBooksByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.findBooksByTitle(title).stream().map(bookMapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(summary = "Find books by ISBN", description = "Returns a set of books based on the provided ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ISBN supplied"),
            @ApiResponse(responseCode = "404", description = "Books not found")
    })
    @GetMapping(value = "/isbn/{isbn}", produces = "application/json")
    public ResponseEntity<Set<BookDTO>> findBooksByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findBooksByIsbn(isbn).stream().map(bookMapper::toDTO).collect(Collectors.toSet()));
    }

    @Operation(summary = "Find books by authors", description = "Returns a list of books written by the provided authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid author details supplied"),
            @ApiResponse(responseCode = "404", description = "Books not found")
    })
    @PostMapping(value = "/authors", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<BookDTO>> findBooksByAuthors(@RequestBody Set<AuthorDTO> authorDTOs) {
        Set<Author> authors = authorDTOs.stream()
                .map(authorMapper::toEntity)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(bookService.findBooksByAuthors(authors).stream()
                .map(bookMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Find books by publisher", description = "Returns a list of books published by the provided publisher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid publisher details supplied"),
            @ApiResponse(responseCode = "404", description = "Books not found")
    })
    @PostMapping(value = "/publisher", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<BookDTO>> findBooksByPublisher(@RequestBody PublisherDTO publisherDTO) {
        Publisher publisher = publisherMapper.toEntity(publisherDTO);
        return ResponseEntity.ok(bookService.findBooksByPublishers(publisher).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Find authors by book ISBN", description = "Returns a list of authors of the book with the provided ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ISBN supplied"),
            @ApiResponse(responseCode = "404", description = "Book or authors not found")
    })
    @GetMapping(value = "/{isbn}/authors", produces = "application/json")
    public ResponseEntity<List<AuthorDTO>> findAuthorsByBookIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findAuthorsByBookIsbn(isbn).stream().map(authorMapper::toDTO).collect(Collectors.toList()));
    }

    @Operation(summary = "Find publisher by book ISBN", description = "Returns the publisher of the book with the provided ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ISBN supplied"),
            @ApiResponse(responseCode = "404", description = "Book or publisher not found")
    })
    @GetMapping(value = "/{isbn}/publisher", produces = "application/json")
    public ResponseEntity<PublisherDTO> findPublisherByBookIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(publisherMapper.toDTO(bookService.findPublisherByBookIsbn(isbn)));
    }

    @Operation(summary = "Create a new book", description = "Creates a new book with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created"),
            @ApiResponse(responseCode = "400", description = "Invalid book details supplied")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> createBook(@RequestBody @Valid BookDTO bookDTO) {
        bookService.createBook(bookMapper.toEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a book by ID", description = "Updates the details of an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated"),
            @ApiResponse(responseCode = "400", description = "Invalid ID or book details supplied"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "Conflict occurred while updating")
    })
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateBookById(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        bookService.updateBookById(id, bookDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update a book by ISBN", description = "Updates the details of an existing book by its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated"),
            @ApiResponse(responseCode = "400", description = "Invalid ISBN or book details supplied"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "Conflict occurred while updating")
    })
    @PutMapping(value = "/isbn/{isbn}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateBookByIsbn(@PathVariable String isbn, @RequestBody @Valid BookDTO bookDTO) {
        bookService.updateBookByIsbn(isbn, bookDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a book by ID", description = "Deletes an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a book by ISBN", description = "Deletes an existing book by its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ISBN supplied"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping(value = "/isbn/{isbn}", produces = "application/json")
    public ResponseEntity<Void> deleteBookByIsbn(@PathVariable String isbn) {
        bookService.deleteBookByISBN(isbn);
        return ResponseEntity.noContent().build();
    }
}
