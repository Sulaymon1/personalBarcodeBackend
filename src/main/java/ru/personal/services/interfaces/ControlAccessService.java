package ru.personal.services.interfaces;

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
}
