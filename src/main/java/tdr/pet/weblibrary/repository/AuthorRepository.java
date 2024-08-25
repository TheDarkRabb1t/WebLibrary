package tdr.pet.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByName(String name);

    Author findAuthorByEmail(String email);

    Author findAuthorByNameAndEmail(String name, String email);
}
