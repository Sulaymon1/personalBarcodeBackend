package ru.personal.services.interfaces;

import ru.personal.dto.Vkontakte.VkUser;

/**
 * Date 25.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface ParserService {
    VkUser parseVK(String url);
}
