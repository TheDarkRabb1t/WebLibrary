package tdr.pet.weblibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.AuthorMapper;
import tdr.pet.weblibrary.model.mapper.BookMapper;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.service.BookService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TestRestBookController {

    @Mock
    private BookService bookService;

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private PublisherMapper publisherMapper;

    @InjectMocks
    private RestBookController restBookController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(bookService.findBooksByAuthors(Mockito.anySet())).thenReturn(new ArrayList<>());
        Mockito.lenient().when(authorMapper.toEntity(Mockito.any(AuthorDTO.class))).thenReturn(new Author());
        mockMvc = MockMvcBuilders.standaloneSetup(restBookController).build();
    }


    @Test
    void testFindBooksByTitle() throws Exception {
        BookDTO bookDTO = new BookDTO("Book Title", "Book Description", 300, "1234567890123", new PublisherDTO("Publisher Name", "Publisher Address"), new HashSet<>(), null, "http://image.url", LocalDateTime.now());
        Book book = new Book();

        List<Book> books = List.of(book);

        when(bookService.findBooksByTitle(anyString())).thenReturn(books);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookDTO);

        mockMvc.perform(get("/api/v1/book/title/{title}", "Book Title"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].title").value("Book Title"))
                .andExpect(jsonPath("$[0].description").value("Book Description"));
    }

    @Test
    void testFindBooksByIsbn() throws Exception {
        BookDTO bookDTO = new BookDTO("Book Title", "Book Description", 300, "1234567890123", new PublisherDTO("Publisher Name", "Publisher Address"), new HashSet<>(), null, "http://image.url", LocalDateTime.now());
        Book book = new Book();

        Set<Book> books = new HashSet<>(List.of(book));

        when(bookService.findBooksByIsbn(anyString())).thenReturn(books);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookDTO);

        mockMvc.perform(get("/api/v1/book/isbn/{isbn}", "1234567890123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].isbn").value("1234567890123"));
    }

    @Test
    void testFindBooksByAuthors() throws Exception {
        when(bookService.findBooksByAuthors(any())).thenReturn(new ArrayList<>());
        when(authorMapper.toEntity(any())).thenReturn(new Author());

        mockMvc.perform(post("/api/v1/book/authors")
                        .contentType("application/json")
                        .content("[{ \"name\": \"Author Name\" }]"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindBooksByPublisher() throws Exception {
        BookDTO bookDTO = new BookDTO("Book Title", "Book Description", 300, "1234567890123", new PublisherDTO("Publisher Name", "Publisher Address"), new HashSet<>(), null, "http://image.url", LocalDateTime.now());
        Book book = new Book();

        PublisherDTO publisherDTO = new PublisherDTO("Publisher Name", "Publisher Address");
        Publisher publisher = new Publisher();
        List<Book> books = List.of(book);

        when(publisherMapper.toEntity(any(PublisherDTO.class))).thenReturn(publisher);
        when(bookService.findBooksByPublishers(any(Publisher.class))).thenReturn(books);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookDTO);

        mockMvc.perform(post("/api/v1/book/publisher")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(publisherDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAuthorsByBookIsbn() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Author Name");
        Author author = new Author();

        List<Author> authors = List.of(author);

        when(bookService.findAuthorsByBookIsbn(anyString())).thenReturn(authors);
        when(authorMapper.toDTO(any(Author.class))).thenReturn(authorDTO);

        mockMvc.perform(get("/api/v1/book/{isbn}/authors", "1234567890123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("Author Name"));
    }

    @Test
    void testFindPublisherByBookIsbn() throws Exception {
        PublisherDTO publisherDTO = new PublisherDTO("Publisher Name", "Publisher Address");
        Publisher publisher = new Publisher();

        when(bookService.findPublisherByBookIsbn(anyString())).thenReturn(publisher);
        when(publisherMapper.toDTO(any(Publisher.class))).thenReturn(publisherDTO);

        mockMvc.perform(get("/api/v1/book/{isbn}/publisher", "1234567890123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Publisher Name"));
    }

    @Test
    void testCreateBook() throws Exception {
        BookDTO bookDTO = new BookDTO("Book Title", "Book Description", 300, "1234567890123", new PublisherDTO("Publisher Name", "Publisher Address"), new HashSet<>(), null, "http://image.url", LocalDateTime.now());
        Book book = new Book();

        when(bookMapper.toEntity(any(BookDTO.class))).thenReturn(book);
        doNothing().when(bookService).createBook(any(Book.class));

        mockMvc.perform(post("/api/v1/book")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(bookDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateBookById() throws Exception {
        BookDTO bookDTO = new BookDTO("Book Title", "Book Description", 300, "1234567890123", new PublisherDTO("Publisher Name", "Publisher Address"), new HashSet<>(), null, "http://image.url", LocalDateTime.now());
        doNothing().when(bookService).updateBookById(anyLong(), any(BookDTO.class));
        mockMvc.perform(put("/api/v1/book/{id}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(bookDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBookByIsbn() throws Exception {
        BookDTO bookDTO = new BookDTO("Book Title", "Book Description", 300, "1234567890123", new PublisherDTO("Publisher Name", "Publisher Address"), new HashSet<>(), null, "http://image.url", LocalDateTime.now());
        doNothing().when(bookService).updateBookByIsbn(anyString(), any(BookDTO.class));
        mockMvc.perform(put("/api/v1/book/isbn/{isbn}", "1234567890123")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(bookDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBookById() throws Exception {
        doNothing().when(bookService).deleteBookById(anyLong());

        mockMvc.perform(delete("/api/v1/book/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBookByIsbn() throws Exception {
        doNothing().when(bookService).deleteBookByISBN(anyString());

        mockMvc.perform(delete("/api/v1/book/isbn/{isbn}", "1234567890123"))
                .andExpect(status().isNoContent());
    }
}
