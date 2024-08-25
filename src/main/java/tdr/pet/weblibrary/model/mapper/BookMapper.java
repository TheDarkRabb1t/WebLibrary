package tdr.pet.weblibrary.model.mapper;

import org.mapstruct.Mapper;
import tdr.pet.weblibrary.model.dto.BookDTO;
import tdr.pet.weblibrary.model.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDTO(Book book);

    Book toEntity(BookDTO bookDTO);
}
