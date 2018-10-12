package ru.personal.components;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.personal.dto.UserProfileDTO;
import ru.personal.models.User;

/**
 * Date 09.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Mapper()
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserProfileDTO userToDto(User user);
}
