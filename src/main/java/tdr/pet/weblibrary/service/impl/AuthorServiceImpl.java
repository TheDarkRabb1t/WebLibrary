package tdr.pet.weblibrary.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tdr.pet.weblibrary.exception.author.AuthorNotFoundException;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.repository.AuthorRepository;
import tdr.pet.weblibrary.service.AuthorService;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAuthorsByName(String name) {
        return authorRepository.findAuthorsByName(name);
    }

    @Override
    public Author findAuthorByName(String email) {
        return authorRepository.findAuthorByEmail(email)
                .orElseThrow(() -> new AuthorNotFoundException("Author with this email wasn't found"));
    }

    @Override
    public boolean exists(String email) {
        return authorRepository.existsByEmail(email);
    }

    @Override
    public void createNewAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void updateAuthorById(Long id, Author author) {
        if (authorRepository.existsById(id)) {
            authorRepository.updateAuthorById(id, author);
        } else {
            throw new AuthorNotFoundException();
        }
    }

    @Override
    public void updateAuthorByEmail(String email, Author author) {
        if (authorRepository.existsByEmail(email)) {
            authorRepository.updateAuthorByEmail(email, author);
        } else {
            throw new AuthorNotFoundException();
        }
    }

    @Override
    public void deleteAuthorById(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new AuthorNotFoundException();
        }
    }

    @Override
    public void deleteAuthorByEmail(String email) {
        if (authorRepository.existsByEmail(email)) {
            authorRepository.deleteAuthorByEmail(email);
        } else {
            throw new AuthorNotFoundException();
        }
    }
}
