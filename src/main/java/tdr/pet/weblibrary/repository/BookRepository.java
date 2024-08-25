package tdr.pet.weblibrary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book getBookByTitle(String title);

    Book getBookByAuthors(Set<Author> authors);

    Book getBookByIsbn(String isbn);

    Book getBooksByPublisher(Publisher publisher);
}
