package tdr.pet.weblibrary.service;

import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.List;
import java.util.Set;

public interface BookService {
    List<Book> getBooksByTitle(String title);

    Book getBookByISBN(String isbn);

    boolean exists(String isbn);

    List<Book> getBooksByAuthors(Set<Author> authors);

    List<Book> getBooksByPublisher(Publisher publisher);

    void createBook(Book book);

    void updateBookById(Long id, Book book);

    void updateBookByIsbn(String isbn, Book book);

    void deleteBookById(Long id);

    void deleteBookByISBN(String isbn);
}
