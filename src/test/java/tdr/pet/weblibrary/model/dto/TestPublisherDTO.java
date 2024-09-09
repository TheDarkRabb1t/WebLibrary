package tdr.pet.weblibrary.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPublisherDTO {
    @Test
    void testCreatePublisherDTO() {
        String name = "John Doe";
        String address = "Ukraine";

        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setName(name);
        publisherDTO.setAddress(address);

        Assertions.assertEquals(name, publisherDTO.getName());
        Assertions.assertEquals(address, publisherDTO.getAddress());
    }
}
