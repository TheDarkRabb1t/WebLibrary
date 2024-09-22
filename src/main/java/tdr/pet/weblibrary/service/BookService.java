package tdr.pet.weblibrary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.List;
import java.util.Set;

public interface BookService {
    Page<Book> getBooks(PageRequest pageRequest);

    List<Book> findBooksByTitle(String title);

    Set<Book> findBooksByIsbn(String isbn);

    boolean exists(String isbn);

    List<Book> findBooksByAuthors(Set<Author> authors);

    List<Book> findBooksByPublishers(Publisher publisher);

    List<Author> findAuthorsByBookIsbn(String isbn);

    List<Author> findAuthorsByBookId(Long id);

    Publisher findPublisherByBookIsbn(String isbn);

    Publisher findPublisherByBookId(Long id);

    void createBook(Book book);

    void updateBookById(Long id, BookDTO book);

    void updateBookByIsbn(String isbn, BookDTO book);

    void deleteBookById(Long id);

    void deleteBookByISBN(String isbn);
}
