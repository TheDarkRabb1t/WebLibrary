package tdr.pet.weblibrary.model.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.entity.Author;

public class TestAuthorMapper {
    private AuthorMapper authorMapper;

    @BeforeEach
    void setUp() {
        authorMapper = Mappers.getMapper(AuthorMapper.class);
    }

    @Test
    void testEntityToDTO() {
        Author author = new Author();
        author.setName("John Doe");
        author.setEmail("example@gmail.com");
        author.setImgUrl("John Doe");

        AuthorDTO authorDTO = authorMapper.toDTO(author);
        Assertions.assertEquals(author.getName(), authorDTO.getName());
        Assertions.assertEquals(author.getEmail(), authorDTO.getEmail());
        Assertions.assertEquals(author.getImgUrl(), authorDTO.getImgUrl());
    }

    @Test
    void testDTOtoEntity() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("John Doe");
        authorDTO.setEmail("example@gmail.com");
        authorDTO.setImgUrl("John Doe");

        Author author = authorMapper.toEntity(authorDTO);
        Assertions.assertEquals(author.getName(), authorDTO.getName());
        Assertions.assertEquals(author.getEmail(), authorDTO.getEmail());
        Assertions.assertEquals(author.getImgUrl(), authorDTO.getImgUrl());
    }
}
