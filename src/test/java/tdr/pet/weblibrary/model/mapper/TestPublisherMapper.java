package tdr.pet.weblibrary.model.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Publisher;

public class TestPublisherMapper {
    private PublisherMapper publisherMapper;

    @BeforeEach
    void setUp() {
        publisherMapper = Mappers.getMapper(PublisherMapper.class);
    }

    @Test
    void testEntityToDTO() {
        Publisher publisher = new Publisher();
        publisher.setName("John Doe");
        publisher.setAddress("Ukraine");

        PublisherDTO publisherDTO = publisherMapper.toDTO(publisher);
        Assertions.assertEquals(publisher.getName(), publisherDTO.getName());
        Assertions.assertEquals(publisher.getAddress(), publisherDTO.getAddress());
    }

    @Test
    void testDTOtoEntity() {
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setName("John Doe");
        publisherDTO.setAddress("Ukraine");

        Publisher author = publisherMapper.toEntity(publisherDTO);
        Assertions.assertEquals(author.getName(), publisherDTO.getName());
        Assertions.assertEquals(author.getAddress(), publisherDTO.getAddress());
    }
}
