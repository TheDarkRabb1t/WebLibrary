package tdr.pet.weblibrary.model.mapper;

import org.mapstruct.Mapper;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Publisher;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherDTO toDTO(Publisher publisher);

    Publisher toEntity(PublisherDTO publisherDTO);
}
