package ru.personal.services.interfaces;

import ru.personal.dto.UserProfileDTO;
import ru.personal.models.User;

import java.util.List;

/**
 * Date 26.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface ControlAccessService {
    UserProfileDTO getUserProfile(String username, String token);
    void addNewRequestUser(String username, String token);

    void accept(String username, String token) throws Exception;

    void decline(String username, String token) throws Exception;

    void deleteFollower(String username, String token) throws Exception;

    List<User> getRequestedUsers(String token);
    List<User> getFollowers(String token);

    void closeProfile(String token, Boolean status);
}
