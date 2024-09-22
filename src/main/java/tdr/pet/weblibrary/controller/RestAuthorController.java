package tdr.pet.weblibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.mapper.AuthorMapper;
import tdr.pet.weblibrary.service.AuthorService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/author")
@Tag(name = "Authors Controller", description = "Endpoints for managing authors")
public class RestAuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Operation(summary = "Get authors", description = "Returns a list of authors based on page values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request params")
    })
    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<List<AuthorDTO>> getAuthors(@RequestParam Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(authorService.getAuthors(PageRequest.of(pageNumber, pageSize)).stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Find authors by name", description = "Returns a list of authors based on the provided name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid name supplied"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<List<AuthorDTO>> findAuthorsByName(@PathVariable String name) {
        return ResponseEntity.ok(authorService.findAuthorsByName(name).stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Find authors by email", description = "Returns a set of authors based on the provided email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid email supplied"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping(value = "/email/{email}", produces = "application/json")
    public ResponseEntity<Set<AuthorDTO>> findAuthorsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authorService.findAuthorsByEmail(email).stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toSet()));
    }

    @Operation(summary = "Create a new author", description = "Creates a new author with the given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> createNewAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorService.createNewAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update an author by ID", description = "Updates an existing author by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID or input"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "409", description = "Conflict occurred while updating")
    })
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateAuthorById(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO) {
        authorService.updateAuthorById(id, authorDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update an author by email", description = "Updates an existing author by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid email or input"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "409", description = "Conflict occurred while updating")
    })
    @PutMapping(value = "/email/{email}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateAuthorByEmail(@PathVariable String email, @RequestBody @Valid AuthorDTO authorDTO) {
        authorService.updateAuthorByEmail(email, authorDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete an author by ID", description = "Deletes an existing author by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Author deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete an author by email", description = "Deletes an existing author by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Author deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid email supplied"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping(value = "/email/{email}", produces = "application/json")
    public ResponseEntity<Void> deleteAuthorByEmail(@PathVariable String email) {
        authorService.deleteAuthorByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
