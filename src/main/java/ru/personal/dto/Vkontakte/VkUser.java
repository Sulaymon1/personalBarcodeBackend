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
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkUser {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("photo_100")
    private String profilePhoto;
    @JsonProperty("last_seen")
    private VkTime lastSeen;
}
