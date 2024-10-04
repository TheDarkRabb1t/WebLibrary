package tdr.pet.weblibrary.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import tdr.pet.weblibrary.model.dto.UserDTO;
import tdr.pet.weblibrary.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);

    void update(UserDTO userDTO, @MappingTarget User user);
}
