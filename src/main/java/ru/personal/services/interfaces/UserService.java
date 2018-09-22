package ru.personal.services.interfaces;

import ru.personal.models.User;

import java.time.LocalDate;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface UserService {
    void saveUser(User user);

    Boolean updateUsername(String token, String username, String imageBytes);

    User getUserByToken(String token);
    void updateUser(String token, String username, String name, String file);

    boolean hasUsername(String username);

    void updateUserInfo(String token, String name, String lastname, LocalDate date);
}
