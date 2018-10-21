package ru.personal.services.interfaces;

import ru.personal.dto.UserDTO;
import ru.personal.dto.UserProfileDTO;
import ru.personal.models.User;

import java.util.Optional;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface UserService {
    void saveUser(User user);
    Optional<User> getUserByUsername(String username);
    Boolean updateUsername(String token, String username, String qrImage);

    User getUserByToken(String token);

    boolean hasUsername(String username);

    void updateUserInfo(String token, String name, String lastname);
    UserProfileDTO getUserDTOByToken(String token);

    UserDTO getUserGuests(String token);
}
