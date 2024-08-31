package tdr.pet.weblibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.repository.AuthorRepository;
import tdr.pet.weblibrary.service.impl.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestAuthorService {

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAuthorsByName() {
        Author author1 = new Author();
        author1.setName("John First");

        Author author2 = new Author();
        author2.setName("John Second");

        when(authorRepository.findAuthorsByName("John")).thenReturn(List.of(author1, author2));

        List<Author> results = authorService.getAuthorsByName("John");
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals("John First", results.get(0).getName());
    }

    @Test
    void getAuthorByEmail() {
        String email = "john.doe@example.com";
        Author author = new Author();
        author.setEmail(email);
        when(authorRepository.findAuthorByEmail(email)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(authorRepository, times(1)).findAuthorByEmail(email);
    }

    @Test
    void exists() {
        String email = "john.doe@example.com";
        when(authorRepository.existsByEmail(email)).thenReturn(true);

        boolean exists = authorService.exists(email);

        assertTrue(exists);
        verify(authorRepository, times(1)).existsByEmail(email);
    }

    @Test
    void createNewAuthor() {
        Author author = new Author();
        author.setName("John Doe");
        author.setEmail("john.doe@example.com");

        authorService.createNewAuthor(author);

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void updateAuthorById() {
        Long id = 1L;
        Author author = new Author();
        author.setName("John Doe");
        author.setEmail("john.doe@example.com");

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        authorService.updateAuthorById(id, author);

        verify(authorRepository, times(1)).updateAuthorById(id, author);
    }

    @Test
    void updateAuthorByEmail() {
        Long id = 1L;
        String email = "john.doe@example.com";
        Author existingAuthor = new Author();
        existingAuthor.setId(id);
        existingAuthor.setEmail(email);

        Author updatedAuthor = new Author();
        updatedAuthor.setName("John Doe");

        when(authorRepository.findAuthorByEmail(email)).thenReturn(Optional.of(existingAuthor));

        authorService.updateAuthorByEmail(email, updatedAuthor);

        verify(authorRepository, times(1)).updateAuthorById(id, updatedAuthor);
    }

    @Test
    void deleteAuthorById() {
        Long id = 1L;

        authorService.deleteAuthorById(id);

        verify(authorRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteAuthorByEmail() {
        Long id = 1L;
        String email = "john.doe@example.com";
        Author author = new Author();
        author.setId(id);
        author.setEmail(email);

        when(authorRepository.findAuthorByEmail(email)).thenReturn(Optional.of(author));

        authorService.deleteAuthorByEmail(email);

        verify(authorRepository, times(1)).deleteAuthorByEmail(email);
    }
}