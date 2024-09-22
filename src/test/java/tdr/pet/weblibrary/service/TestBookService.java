package tdr.pet.weblibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.BookMapper;
import tdr.pet.weblibrary.repository.BookRepository;
import tdr.pet.weblibrary.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestBookService {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    private Book book;
    private Author author;
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setEmail("author@example.com");

        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Sample Publisher");

        book = new Book();
        book.setId(1L);
        book.setIsbn("1234567890");
        book.setTitle("Sample Book");
        book.setAuthors(Set.of(author));
        book.setPublisher(publisher);
    }

    @Test
    void testGetBooksWithPagination() {
        PageRequest pageRequest = PageRequest.of(0, 2);

        List<Book> books = List.of(book);
        Page<Book> bookPage = new PageImpl<>(books, pageRequest, books.size());

        when(bookRepository.findAll(pageRequest)).thenReturn(bookPage);

        Page<Book> result = bookServiceImpl.getBooks(pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(book.getTitle(), result.getContent().get(0).getTitle());

        verify(bookRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void testFindBooksByTitle() {
        when(bookRepository.getBooksByTitle("Sample Book")).thenReturn(List.of(book));

        List<Book> books = bookServiceImpl.findBooksByTitle("Sample Book");

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book.getTitle(), books.get(0).getTitle());
        verify(bookRepository, times(1)).getBooksByTitle("Sample Book");
    }

    @Test
    void testFindBooksByIsbn() {
        when(bookRepository.findBooksByIsbn("1234567890")).thenReturn(Set.of(book));

        Set<Book> books = bookServiceImpl.findBooksByIsbn("1234567890");

        assertNotNull(books);
        assertEquals(1, books.size());
        assertTrue(books.contains(book));
        verify(bookRepository, times(1)).findBooksByIsbn("1234567890");
    }

    @Test
    void testFindBooksByIsbn_emptyList() {
        when(bookRepository.findBooksByIsbn("1234567890")).thenReturn(Set.of());

        Set<Book> books = bookServiceImpl.findBooksByIsbn("1234567890");

        assertNotNull(books);
        assertEquals(0, books.size());
        verify(bookRepository, times(1)).findBooksByIsbn("1234567890");
    }

    @Test
    void testExists() {
        when(bookRepository.existsByIsbn("1234567890")).thenReturn(true);

        boolean exists = bookServiceImpl.exists("1234567890");

        assertTrue(exists);
        verify(bookRepository, times(1)).existsByIsbn("1234567890");
    }

    @Test
    void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookServiceImpl.createBook(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDTO bookDTO = bookMapper.toDTO(book);
        bookServiceImpl.updateBookById(1L, bookDTO);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBookByIsbn() {
        when(bookRepository.findBooksByIsbn("1234567890")).thenReturn(Set.of(book));

        BookDTO bookDTO = bookMapper.toDTO(book);
        bookServiceImpl.updateBookByIsbn("1234567890", bookDTO);

        verify(bookRepository, times(1)).findBooksByIsbn("1234567890");
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testDeleteBookById() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookServiceImpl.deleteBookById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookByISBN() {
        when(bookRepository.existsByIsbn("1234567890")).thenReturn(true);
        doNothing().when(bookRepository).deleteBookByIsbn("1234567890");

        bookServiceImpl.deleteBookByISBN("1234567890");

        verify(bookRepository, times(1)).deleteBookByIsbn("1234567890");
    }
}
