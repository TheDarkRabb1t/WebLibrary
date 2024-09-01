package tdr.pet.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAuthorsByName(String name);

    Set<Author> findAuthorsByEmail(String email);

    boolean existsByEmail(String email);

    void updateAuthorById(Long id, Author author);

    void updateAuthorByEmail(String email, Author author);

    void deleteAuthorByEmail(String email);
}
