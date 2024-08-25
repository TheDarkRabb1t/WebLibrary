package tdr.pet.weblibrary.service;

import tdr.pet.weblibrary.model.entity.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthorsByName(String name);

    Author getAuthorByEmail(String email);

    Author getAuthorByNameAndEmail(String name, String email);

    void createNewAuthor(Author author);

    void updateAuthorById(Long id, Author author);

    void updateAuthorByEmail(String email, Author author);

    void deleteAuthorById(Long id);

    void deleteAuthorByEmail(String email);

}