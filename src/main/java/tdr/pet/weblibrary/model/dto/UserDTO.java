package tdr.pet.weblibrary.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Size(min = 3, max = 50)
    private String firstName;
    @Size(min = 3, max = 50)
    private String lastName;
    @Size(min = 4, max = 32)
    private String username;
    @Email(message = "incorrect email syntax")
    private String email;
    @Size(min = 6, max = 48)
    private String password;
}