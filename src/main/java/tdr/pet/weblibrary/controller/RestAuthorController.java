package tdr.pet.weblibrary.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.mapper.AuthorMapper;
import tdr.pet.weblibrary.service.AuthorService;

@RestController("/api/v1/author")
@AllArgsConstructor
public class RestAuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping("/{authorEmail}")
    public ResponseEntity<AuthorDTO> getAuthorByName(@PathVariable String authorEmail, BindingResult bindingResult) {
        if (ObjectUtils.isEmpty(authorEmail) || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authorMapper.toDTO(authorService.findAuthorByName(authorEmail)));
    }

    @PostMapping("/create")
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        authorService.createNewAuthor(authorMapper.toEntity(authorDTO));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{authorEmail}")
    public ResponseEntity<AuthorDTO> updateAuthorByEmail(@PathVariable String authorEmail, @Valid @RequestBody AuthorDTO authorDTO, BindingResult bindingResult) {
        if (ObjectUtils.isEmpty(authorEmail) || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        authorService.updateAuthorByEmail(authorEmail, authorMapper.toEntity(authorDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{authorEmail}")
    public ResponseEntity<Void> deleteAuthorByEmail(@PathVariable String authorEmail, BindingResult bindingResult) {
        if (ObjectUtils.isEmpty(authorEmail) || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
