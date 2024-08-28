package tdr.pet.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Author;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAuthorsByName(String name);

    Optional<Author> findAuthorByEmail(String email);

    boolean existsByEmail(String email);

    Author updateAuthorById(Long id, Author author);

    void deleteAuthorById(Long id);

}
