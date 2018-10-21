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
public class GroupListDTO {
    private List<String> title;
    private List<Long> groupID;
    private List<String> groupPhoto;
    private List<Long> membersCount;
}
