package tdr.pet.weblibrary.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAuthorDTO {
    @Test
    void testCreateAuthorDTO() {
        String name = "John Doe";
        String email = "example@gmail.com";

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(name);
        authorDTO.setEmail(email);

        Assertions.assertEquals(name, authorDTO.getName());
        Assertions.assertEquals(email, authorDTO.getEmail());
    }
}
