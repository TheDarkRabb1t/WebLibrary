package tdr.pet.weblibrary.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tdr.pet.weblibrary.model.entity.Genre;
import tdr.pet.weblibrary.model.validation.annotation.ValidIsbn;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String description;
    private int pages;
    @ValidIsbn
    private String isbn;
    private Genre genre;
    private String imgUrl;
    private LocalDateTime written;
}
