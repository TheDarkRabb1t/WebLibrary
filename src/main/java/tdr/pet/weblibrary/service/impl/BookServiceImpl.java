package tdr.pet.weblibrary.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tdr.pet.weblibrary.exception.book.BookNotFoundException;
import tdr.pet.weblibrary.exception.publisher.PublisherNotFoundException;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.BookMapper;
import tdr.pet.weblibrary.repository.BookRepository;
import tdr.pet.weblibrary.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.getBooksByTitle(title);
    }

    @Override
    public List<Book> findBooksByAuthors(Set<Author> authors) {
        return bookRepository.getBooksByAuthors(authors);
    }

    @Override
    public Set<Book> findBooksByIsbn(String isbn) {
        return bookRepository.findBooksByIsbn(isbn);
    }

    @Override
    public boolean exists(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public List<Book> findBooksByPublishers(Publisher publisher) {
        return bookRepository.getBooksByPublisher(publisher);
    }

    @Override
    public List<Author> findAuthorsByBookIsbn(String isbn) {
        return bookRepository.findAuthorsByBookIsbn(isbn);
    }

    @Override
    public List<Author> findAuthorsByBookId(Long id) {
        return bookRepository.findAuthorsByBookId(id);
    }

    @Override
    public Publisher findPublisherByBookIsbn(String isbn) {
        return bookRepository.findPublisherByBookIsbn(isbn)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher with this book's isbn not found"));
    }

    @Override
    public Publisher findPublisherByBookId(Long id) {
        return bookRepository.findPublisherByBookId(id);
    }

    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void updateBookById(Long id, BookDTO bookDTO) {
        Optional<Book> foundBook = bookRepository.findById(id);
        if (foundBook.isPresent()) {
            bookMapper.update(bookDTO, foundBook.get());
            bookRepository.save(foundBook.get());
        } else {
            throw new BookNotFoundException();
        }
    }

    @Override
    public void updateBookByIsbn(String isbn, BookDTO bookDTO) {
        Set<Book> books = bookRepository.findBooksByIsbn(isbn);
        if (books.size() == 1) {
            Book book = books.stream().findFirst().get();
            bookMapper.update(bookDTO, book);
            bookRepository.save(book);
        } else if (books.size() > 1) {
            throw new MultipleBooksFoundException();
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
