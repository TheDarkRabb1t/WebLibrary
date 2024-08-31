package tdr.pet.weblibrary.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tdr.pet.weblibrary.exception.book.BookNotFoundException;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.repository.BookRepository;
import tdr.pet.weblibrary.service.BookService;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.getBooksByTitle(title);
    }

    @Override
    public List<Book> getBooksByAuthors(Set<Author> authors) {
        return bookRepository.getBooksByAuthors(authors);
    }

    @Override
    public Book getBookByISBN(String isbn) {
        return bookRepository.getBookByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book with given isbn wasn't found"));
    }

    @Override
    public boolean exists(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public List<Book> getBooksByPublisher(Publisher publisher) {
        return bookRepository.getBooksByPublisher(publisher);
    }

    @Override
    public List<Author> findAuthorsByBookId(Long id) {
        return bookRepository.findAuthorsByBookId(id);
    }

    @Override
    public List<Publisher> findPublishersByBookId(Long id) {
        return bookRepository.findPublishersByBookId(id);
    }

    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void updateBookById(Long id, Book book) {
        if (bookRepository.existsById(id)) {
            bookRepository.updateBookById(id, book);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Override
    public void updateBookByIsbn(String isbn, Book book) {
        if (bookRepository.existsByIsbn(isbn)) {
            bookRepository.updateBookByIsbn(isbn, book);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Override
    public void deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Override
    public void deleteBookByISBN(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            bookRepository.deleteBookByIsbn(isbn);
        } else {
            throw new BookNotFoundException();
        }
    }
}
