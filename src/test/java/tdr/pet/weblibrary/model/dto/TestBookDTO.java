package tdr.pet.weblibrary.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tdr.pet.weblibrary.model.entity.Genre;

import java.time.LocalDateTime;

public class TestBookDTO {
    @Test
    void testCreateBookDTO() {
        String title = "The book";
        int pages = 134;
        String isbn = "978-3-16-148410-0";
        Genre genre = Genre.COMEDY;
        String imgUrl = "https://www.google.com";
        LocalDateTime written = LocalDateTime.now();

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(title);
        bookDTO.setPages(pages);
        bookDTO.setIsbn(isbn);
        bookDTO.setGenre(genre);
        bookDTO.setImgUrl(imgUrl);
        bookDTO.setWritten(written);

        Assertions.assertEquals(title, bookDTO.getTitle());
        Assertions.assertEquals(pages, bookDTO.getPages());
        Assertions.assertEquals(isbn, bookDTO.getIsbn());
        Assertions.assertEquals(genre, bookDTO.getGenre());
        Assertions.assertEquals(imgUrl, bookDTO.getImgUrl());
        Assertions.assertEquals(written, bookDTO.getWritten());
    }
}
