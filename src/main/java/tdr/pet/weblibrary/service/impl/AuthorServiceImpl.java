package tdr.pet.weblibrary.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tdr.pet.weblibrary.exception.author.AuthorNotFoundException;
import tdr.pet.weblibrary.exception.author.MultipleAuthorsFoundException;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.mapper.AuthorMapper;
import tdr.pet.weblibrary.repository.AuthorRepository;
import tdr.pet.weblibrary.service.AuthorService;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<Author> findAuthorsByName(String name) {
        return authorRepository.findAuthorsByName(name);
    }

    @Override
    public Set<Author> findAuthorsByEmail(String email) {
        return authorRepository.findAuthorsByEmail(email);
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
    public void updateAuthorById(Long id, AuthorDTO authorDTO) {
        Author foundAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Couldn't found author by id"));
        authorMapper.update(authorDTO, foundAuthor);
        authorRepository.save(foundAuthor);
    }

    @Override
    public void updateAuthorByEmail(String email, AuthorDTO authorDTO) {
        Set<Author> authors = authorRepository.findAuthorsByEmail(email);
        if (authors.size() > 1) {
            throw new MultipleAuthorsFoundException();
        }
        Author foundAuthor = authors.stream().findFirst()
                .orElseThrow(() -> new AuthorNotFoundException("Couldn't found author by email"));
        authorMapper.update(authorDTO, foundAuthor);
        authorRepository.save(foundAuthor);
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
