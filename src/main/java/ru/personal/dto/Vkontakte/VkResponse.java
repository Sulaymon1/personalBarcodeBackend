package ru.personal.dto.Vkontakte;

import lombok.Data;

import java.util.List;

/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Data
public class VkResponse {
    private List<VkUser> response;
}
