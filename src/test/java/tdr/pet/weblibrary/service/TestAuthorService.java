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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestAuthorService {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorServiceImpl;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setEmail("author@example.com");
        author.setName("John Doe");
    }

    @Test
    void testFindAuthorsByName() {
        when(authorRepository.findAuthorsByName("John Doe")).thenReturn(List.of(author));

        List<Author> authors = authorServiceImpl.findAuthorsByName("John Doe");

        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals(author.getName(), authors.get(0).getName());
        verify(authorRepository, times(1)).findAuthorsByName("John Doe");
    }

    @Test
    void testFindAuthorsByEmail() {
        when(authorRepository.findAuthorsByEmail("author@example.com")).thenReturn(Set.of(author));

        Set<Author> authors = authorServiceImpl.findAuthorsByEmail("author@example.com");

        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertTrue(authors.contains(author));
        verify(authorRepository, times(1)).findAuthorsByEmail("author@example.com");
    }

    @Test
    void testExists() {
        when(authorRepository.existsByEmail("author@example.com")).thenReturn(true);

        boolean exists = authorServiceImpl.exists("author@example.com");

        assertTrue(exists);
        verify(authorRepository, times(1)).existsByEmail("author@example.com");
    }

    @Test
    void testCreateNewAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorServiceImpl.createNewAuthor(author);

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testUpdateAuthorById() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(authorRepository).updateAuthorById(1L, author);

        authorServiceImpl.updateAuthorById(1L, author);

        verify(authorRepository, times(1)).updateAuthorById(1L, author);
    }

    @Test
    void testUpdateAuthorByEmail() {
        when(authorRepository.existsByEmail("author@example.com")).thenReturn(true);
        doNothing().when(authorRepository).updateAuthorByEmail("author@example.com", author);
        authorServiceImpl.updateAuthorByEmail("author@example.com", author);

        verify(authorRepository, times(1)).updateAuthorByEmail("author@example.com", author);
    }

    @Test
    void testDeleteAuthorById() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(authorRepository).deleteById(1L);

        authorServiceImpl.deleteAuthorById(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAuthorByEmail() {
        when(authorRepository.existsByEmail("author@example.com")).thenReturn(true);
        doNothing().when(authorRepository).deleteAuthorByEmail("author@example.com");

        authorServiceImpl.deleteAuthorByEmail("author@example.com");

        verify(authorRepository, times(1)).deleteAuthorByEmail("author@example.com");
    }
}