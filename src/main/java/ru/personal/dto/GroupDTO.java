package ru.personal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Date 21.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@Builder
public class GroupDTO {

    private String title;
    private Long groupID;

    private Boolean isAdmin;

    private String groupPhoto;
    private Double longitude; // setting meeting
    private Double latitude;  //  location
    private Long meetingTime; // unixtime
}
