package tdr.pet.weblibrary.model.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.entity.Book;
import tdr.pet.weblibrary.model.entity.Genre;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestBookMapper {

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = Mappers.getMapper(BookMapper.class);
    }

    @Test
    void testToDTO() {
        LocalDateTime now = LocalDateTime.now();

        Book book = new Book();
        book.setTitle("Book");
        book.setDescription("the book");
        book.setPages(123);
        book.setIsbn("978-0134685991");
        book.setGenre(Genre.HORROR);
        book.setImgUrl("https://www.google.com");
        book.setWritten(now);

        BookDTO bookDTO = bookMapper.toDTO(book);

        assertNotNull(bookDTO);
        assertEquals(book.getTitle(), bookDTO.getTitle());
        assertEquals(book.getDescription(), bookDTO.getDescription());
        assertEquals(book.getPages(), bookDTO.getPages());
        assertEquals(book.getIsbn(), bookDTO.getIsbn());
        assertEquals(book.getGenre(), bookDTO.getGenre());
        assertEquals(book.getImgUrl(), bookDTO.getImgUrl());
        assertEquals(book.getWritten(), bookDTO.getWritten());
    }

    @Test
    void testToEntity() {
        LocalDateTime now = LocalDateTime.now();

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Book");
        bookDTO.setDescription("the book");
        bookDTO.setPages(123);
        bookDTO.setIsbn("978-0134685991");
        bookDTO.setGenre(Genre.HORROR);
        bookDTO.setImgUrl("https://www.google.com");
        bookDTO.setWritten(now);

        Book book = bookMapper.toEntity(bookDTO);

        assertNotNull(book);
        assertEquals(bookDTO.getTitle(), book.getTitle());
        assertEquals(bookDTO.getDescription(), book.getDescription());
        assertEquals(bookDTO.getPages(), book.getPages());
        assertEquals(bookDTO.getIsbn(), book.getIsbn());
        assertEquals(bookDTO.getGenre(), book.getGenre());
        assertEquals(bookDTO.getImgUrl(), book.getImgUrl());
        assertEquals(bookDTO.getWritten(), book.getWritten());
    }
}
