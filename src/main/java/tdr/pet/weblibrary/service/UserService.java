package tdr.pet.weblibrary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tdr.pet.weblibrary.model.dto.UserDTO;
import tdr.pet.weblibrary.model.entity.User;

public interface UserService {
    Page<User> getUsers(PageRequest pageRequest);

    User findByEmailOrUsername(String email, String username);
    boolean existsByEmailOrUsername(String email, String username);

    void createNewUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void deleteUserById(Long id);
}
