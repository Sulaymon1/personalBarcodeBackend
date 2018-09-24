package ru.personal.dto.Vkontakte;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
public class VkTime {
    @JsonProperty("time")
    private Integer unixTime;
    private Integer platform;

}
