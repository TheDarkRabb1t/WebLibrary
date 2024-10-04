package tdr.pet.weblibrary.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tdr.pet.weblibrary.model.enums.Genre;
import tdr.pet.weblibrary.model.validation.annotation.ValidIsbn;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @NotNull
    private PublisherDTO publisherDTO;
    @NotNull
    private Set<AuthorDTO> authors = new HashSet<>();
    private Genre genre;
    private String imgUrl;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime written;
}
