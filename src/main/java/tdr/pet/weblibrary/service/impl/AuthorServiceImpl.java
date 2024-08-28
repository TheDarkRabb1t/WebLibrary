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
    public List<Author> getAuthorsByName(String name) {
        return authorRepository.findAuthorsByName(name);
    }

    @Override
    public Author getAuthorByEmail(String email) {
        return authorRepository.findAuthorByEmail(email)
                .orElseThrow(() -> new AuthorNotFoundException("Author with this email wasn't found"));
    }

    @Override
    public void createNewAuthor(Author author) {

    }

    @Override
    public void updateAuthorById(Long id, Author author) {
        authorRepository.updateAuthorById(id, author);
    }

    @Override
    public void updateAuthorByEmail(String email, Author author) {
        authorRepository.updateAuthorById(getAuthorByEmail(email).getId(), author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteAuthorById(id);
    }

    @Override
    public void deleteAuthorByEmail(String email) {
        authorRepository.deleteAuthorById(getAuthorByEmail(email).getId());
    }
}
