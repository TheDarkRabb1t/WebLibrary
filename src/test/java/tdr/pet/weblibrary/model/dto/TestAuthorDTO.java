package tdr.pet.weblibrary.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAuthorDTO {
    @Test
    void testCreateAuthorDTO() {
        String name = "John Doe";
        String email = "example@gmail.com";
        String imgUrl = "https://www.google.com";

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(name);
        authorDTO.setEmail(email);
        authorDTO.setImgUrl(imgUrl);

        Assertions.assertEquals(name, authorDTO.getName());
        Assertions.assertEquals(email, authorDTO.getEmail());
        Assertions.assertEquals(imgUrl, authorDTO.getImgUrl());
    }
}
