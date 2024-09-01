package tdr.pet.weblibrary.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

@RestController("/api/v1/author")
@AllArgsConstructor
public class RestAuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping("/name/{name}")
    public ResponseEntity<List<AuthorDTO>> findAuthorsByName(@PathVariable String name) {
        return ResponseEntity.ok(authorService.findAuthorsByName(name).stream().map(authorMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Set<AuthorDTO>> findAuthorsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authorService.findAuthorsByEmail(email).stream().map(authorMapper::toDTO).collect(Collectors.toSet()));
    }

    @PostMapping
    public ResponseEntity<Void> createNewAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorService.createNewAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthorById(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        authorService.updateAuthorById(id, author);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<Void> updateAuthorByEmail(@PathVariable String email, @RequestBody @Valid AuthorDTO authorDTO) {
        authorService.updateAuthorByEmail(email, authorMapper.toEntity(authorDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteAuthorByEmail(@PathVariable String email) {
        authorService.deleteAuthorByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
