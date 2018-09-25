package ru.personal.dto.Vkontakte;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Setter
@Getter
public class VkUser {
    private String fullName;
    private String profilePhoto;
    private String lastSeen;
}
