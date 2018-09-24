package ru.personal.services.interfaces;

import ru.personal.dto.SocialNetworkData;
import ru.personal.models.User;

/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface SocialNetworkService {

    SocialNetworkData getInfo(User user);

    void add(User user, String profileId, String socialType);
}
