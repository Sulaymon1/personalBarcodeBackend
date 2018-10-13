package ru.personal.mapper;

import org.springframework.stereotype.Component;
import ru.personal.dto.UserProfileDTO;
import ru.personal.models.User;

/**
 * Date 13.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Component
public class UserMapper {

    public UserProfileDTO toUserDTO(User user){
        UserProfileDTO build = UserProfileDTO.builder().name(user.getName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday())
                .coverPhotoPath(user.getCoverPhotoPath())
                .profilePhotoPath(user.getProfilePhotoPath())
                .qrImagePath(user.getQrImagePath())
                .socialNetwork(user.getSocialNetwork())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .city(user.getCity())
                .country(user.getCountry())
                .bCity(user.getBCity())
                .bCountry(user.getBCountry())
                .bExtra(user.getBExtra())
                .status(user.getStatus())
                .withUsername(user.getWithUsername())
                .build();


        return build;
    }
}
