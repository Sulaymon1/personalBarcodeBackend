package ru.personal.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date 20.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public enum FriendStatus {
    @JsonProperty("0")
    REQUEST_SENT,
    @JsonProperty("1")
    FRIENDS,
    @JsonProperty("2")
    SEND_REQUEST,
    @JsonProperty("3")
    REPLY_REQUEST
}
