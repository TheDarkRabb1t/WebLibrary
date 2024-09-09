package tdr.pet.weblibrary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    Set<Book> findBooksByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    @Query("""
            select a.*
            from authors as a
            join book_authors as ba on a.id = ba.authors_id
            join books as b on ba.book_id = b.id
            where b.id = :book_id""")
    List<Author> findAuthorsByBookId(Long id);

    @Query("""
            select distinct a
            from authors as a
            join a.books as b
            where b.isbn like %:isbn%""")
    List<Author> findAuthorsByBookIsbn(String isbn);

    @Query("""
            select p.*
            from publishers as p
                     join books as b on p.id = b.publisher_id
            where b.id = :book_id""")
    Publisher findPublisherByBookId(Long id);

    @Query("""
            select p
            from publishers as p
            join p.books as b
            where b.isbn like %:isbn%""")
    Optional<Publisher> findPublisherByBookIsbn(String isbn);

    List<Book> getBooksByAuthors(Set<Author> authors);

    List<Book> getBooksByPublisher(Publisher publisher);

    void deleteBookByIsbn(String isbn);
}
