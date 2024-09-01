package tdr.pet.weblibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.repository.BookRepository;
import tdr.pet.weblibrary.service.impl.BookServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestBookService {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private Author author;
    private Publisher publisher;
    private Set<Author> authors;
    private List<Book> books;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        author.setEmail("johndoe@example.com");
        author.setImgUrl("image-url");

        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Publisher Name");
        publisher.setAddress("Publisher Address");

        book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setDescription("Description");
        book.setImgUrl("image-url");
        book.setPages(123);
        book.setIsbn("123-4567890123");
        book.setPublisher(publisher);
        book.setAuthors(authors);

        authors = new HashSet<>(Collections.singletonList(author));
        books = List.of(book);
    }

    @Test
    void testGetBooksByTitle() {
        String title = "Book Title";
        when(bookRepository.getBooksByTitle(title)).thenReturn(books);

        List<Book> result = bookService.findBooksByTitle(title);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
        verify(bookRepository, times(1)).getBooksByTitle(title);
    }

    @Test
    void testGetBookByISBN() {
        String isbn = "123-4567890123";
        when(bookRepository.getBookByIsbn(isbn)).thenReturn(Optional.of(book));

        Book result = bookService.findBooksByIsbn(isbn);

        assertNotNull(result);
        assertEquals(book, result);
        verify(bookRepository, times(1)).getBookByIsbn(isbn);
    }

    @Test
    void testExists() {
        String isbn = "123-4567890123";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);

        boolean result = bookService.exists(isbn);

        assertTrue(result);
        verify(bookRepository, times(1)).existsByIsbn(isbn);
    }

    @Test
    void testGetBooksByAuthors() {
        when(bookRepository.getBooksByAuthors(authors)).thenReturn(books);

        List<Book> result = bookService.findBooksByAuthors(authors);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
        verify(bookRepository, times(1)).getBooksByAuthors(authors);
    }

    @Test
    void testGetBooksByPublisher() {
        when(bookRepository.getBooksByPublisher(publisher)).thenReturn(books);

        List<Book> result = bookService.findBooksByPublishers(publisher);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
        verify(bookRepository, times(1)).getBooksByPublisher(publisher);
    }

    @Test
    void testFindAuthorsByBookId() {
        Long id = 1L;
        when(bookRepository.findAuthorsByBookId(id)).thenReturn(authors.stream().toList());

        List<Author> result = bookService.findAuthorsByBookId(id);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(author, result.get(0));
        verify(bookRepository, times(1)).findAuthorsByBookId(id);
    }

    @Test
    void testFindPublishersByBookId() {
        Long id = 1L;
        when(bookRepository.findPublishersByBookId(id)).thenReturn(List.of(publisher));

        List<Publisher> result = bookService.findPublishersByBookId(id);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(publisher, result.get(0));
        verify(bookRepository, times(1)).findPublishersByBookId(id);
    }

    @Test
    void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.createBook(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBookById() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(true);

        bookService.updateBookById(id, book);

        verify(bookRepository, times(1)).existsById(id);
        verify(bookRepository, times(1)).updateBookById(id, book);
    }

    @Test
    void testUpdateBookByIsbn() {
        String isbn = "123-4567890123";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);

        bookService.updateBookByIsbn(isbn, book);

        verify(bookRepository, times(1)).existsByIsbn(isbn);
        verify(bookRepository, times(1)).updateBookByIsbn(isbn, book);
    }

    @Test
    void testDeleteBookById() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(id);

        bookService.deleteBookById(id);

        verify(bookRepository, times(1)).existsById(id);
        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteBookByISBN() {
        String isbn = "123-4567890123";
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        doNothing().when(bookRepository).deleteBookByIsbn(isbn);

        bookService.deleteBookByISBN(isbn);

        verify(bookRepository, times(1)).existsByIsbn(isbn);
        verify(bookRepository, times(1)).deleteBookByIsbn(isbn);
    }
}
