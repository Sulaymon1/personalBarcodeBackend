package ru.personal.services.interfaces;

import ru.personal.dto.SocialUser;
import ru.personal.models.User;

/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface SocialNetworkService {

    SocialUser  getInfo(String token);

    void add(User user, String profileId, String socialType);
}
