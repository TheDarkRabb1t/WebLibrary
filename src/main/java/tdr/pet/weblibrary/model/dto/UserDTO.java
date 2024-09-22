package tdr.pet.weblibrary.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tdr.pet.weblibrary.model.entity.UserRole;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private String username;
    @Email(message = "email required")
    private String email;
    private UserRole userRole;

    @NotNull
    @Size(min = 6, max = 32)
    private String password;
}