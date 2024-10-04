package tdr.pet.weblibrary.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tdr.pet.weblibrary.exception.user.UserCreationException;
import tdr.pet.weblibrary.exception.user.UserNotFoundException;
import tdr.pet.weblibrary.model.dto.UserDTO;
import tdr.pet.weblibrary.model.entity.User;
import tdr.pet.weblibrary.model.enums.UserRole;
import tdr.pet.weblibrary.model.mapper.UserMapper;
import tdr.pet.weblibrary.repository.UserRepository;
import tdr.pet.weblibrary.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<User> getUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public User findByEmailOrUsername(String email, String username) {
        return userRepository.findByEmailOrUsername(email, username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public boolean existsByEmailOrUsername(String email, String username) {
        return userRepository.findByEmailOrUsername(email, username).isPresent();
    }

    @Override
    public void createNewUser(UserDTO userDTO) {
        if (existsByEmailOrUsername(userDTO.getEmail(), userDTO.getUsername())) {
            throw new UserCreationException();
        }
        User entity = userMapper.toEntity(userDTO);
        entity.setUserRole(UserRole.READER);
        userRepository.save(entity);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User foundUser = userRepository.findByEmailOrUsername(userDTO.getEmail(), userDTO.getUsername())
                .orElseThrow(UserNotFoundException::new);
        userMapper.update(userDTO, foundUser);
        userRepository.save(foundUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
