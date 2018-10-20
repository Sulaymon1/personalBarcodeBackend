package ru.personal.services.interfaces;

import ru.personal.dto.UserDTO;
import ru.personal.dto.UserProfileDTO;

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

    void deleteFriend(String username, String token) throws Exception;

    UserDTO getRequestedUsers(String token);
    UserDTO getFriends(String token);

    void closeProfile(String token, Boolean status);

    void cancelRequest(String username, String token) throws Exception;
}
