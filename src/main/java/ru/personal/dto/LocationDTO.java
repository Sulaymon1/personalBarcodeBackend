package ru.personal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Date 15.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@Builder
public class LocationDTO {
    private List<String> username;
    private List<String> name;
    private List<String> lastName;
    private List<String> profilePhotoPath;
    private List<Double> longitude;
    private List<Double> latitude;
    private List<Long> unixtime;
}
