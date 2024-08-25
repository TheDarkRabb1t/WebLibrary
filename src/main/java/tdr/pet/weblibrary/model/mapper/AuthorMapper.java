package tdr.pet.weblibrary.model.mapper;

import org.mapstruct.Mapper;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.entity.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDTO authorDTO);
}
