package tdr.pet.weblibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import tdr.pet.weblibrary.exception.author.AuthorNotFoundException;
import tdr.pet.weblibrary.exception.author.MultipleAuthorsFoundException;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.entity.Author;
import tdr.pet.weblibrary.model.mapper.AuthorMapper;
import tdr.pet.weblibrary.repository.AuthorRepository;
import tdr.pet.weblibrary.service.impl.AuthorServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestAuthorService {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

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
    void testGetAuthorsWithPagination() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<Author> authors = List.of(author);
        Page<Author> authorPage = new PageImpl<>(authors, pageRequest, authors.size());

        when(authorRepository.findAll(pageRequest)).thenReturn(authorPage);

        Page<Author> result = authorServiceImpl.getAuthors(pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(author.getName(), result.getContent().get(0).getName());

        verify(authorRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void testGetAuthorsWithEmptyPage() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Author> emptyPage = Page.empty(pageRequest);

        when(authorRepository.findAll(pageRequest)).thenReturn(emptyPage);

        Page<Author> result = authorServiceImpl.getAuthors(pageRequest);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());

        verify(authorRepository, times(1)).findAll(pageRequest);
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
    void testUpdateAuthorById_Success() {
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        Author foundAuthor = new Author();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(foundAuthor));

        authorServiceImpl.updateAuthorById(authorId, authorDTO);

        verify(authorMapper).update(authorDTO, foundAuthor);
        verify(authorRepository).save(foundAuthor);
    }

    @Test
    void testUpdateAuthorById_AuthorNotFound() {
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> authorServiceImpl.updateAuthorById(authorId, authorDTO));
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void testUpdateAuthorByEmail_Success() {
        String email = "test@example.com";
        AuthorDTO authorDTO = new AuthorDTO();
        Author foundAuthor = new Author();
        Set<Author> authors = Collections.singleton(foundAuthor);
        when(authorRepository.findAuthorsByEmail(email)).thenReturn(authors);
        authorServiceImpl.updateAuthorByEmail(email, authorDTO);
        verify(authorMapper).update(authorDTO, foundAuthor);
        verify(authorRepository).save(foundAuthor);
    }

    @Test
    void testUpdateAuthorByEmail_MultipleAuthorsFound() {
        String email = "test@example.com";
        AuthorDTO authorDTO = new AuthorDTO();
        Set<Author> authors = new HashSet<>(Arrays.asList(new Author(), new Author()));
        when(authorRepository.findAuthorsByEmail(email)).thenReturn(authors);
        assertThrows(MultipleAuthorsFoundException.class, () ->
                authorServiceImpl.updateAuthorByEmail(email, authorDTO)
        );
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void testUpdateAuthorByEmail_AuthorNotFound() {
        // Arrange
        String email = "test@example.com";
        AuthorDTO authorDTO = new AuthorDTO();
        Set<Author> authors = Collections.emptySet();

        when(authorRepository.findAuthorsByEmail(email)).thenReturn(authors);

        // Act & Assert
        assertThrows(AuthorNotFoundException.class, () ->
                authorServiceImpl.updateAuthorByEmail(email, authorDTO)
        );

        verify(authorRepository, never()).save(any(Author.class));
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