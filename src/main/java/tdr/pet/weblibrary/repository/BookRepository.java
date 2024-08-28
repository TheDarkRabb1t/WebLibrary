package tdr.pet.weblibrary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> getBooksByTitle(String title);

    Optional<Book> getBookByIsbn(String isbn);

    List<Book> getBooksByAuthors(Set<Author> authors);

    List<Book> getBooksByPublisher(Publisher publisher);

    void updateBookById(Long id, Book book);

    void updateBookByIsbn(String isbn, Book book);

    void deleteBookByIsbn(String isbn);
}
