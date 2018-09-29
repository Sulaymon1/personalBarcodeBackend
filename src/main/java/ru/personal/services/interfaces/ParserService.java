package ru.personal.services.interfaces;

import ru.personal.dto.SocialUser;

/**
 * Date 25.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface ParserService {
    SocialUser getSocialUser();

    void setSocialUser(SocialUser socialUser);

    void parseVK(String profileId);
    void parseFB(String profileId);
}
