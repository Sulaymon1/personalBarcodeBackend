package ru.personal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Date 12.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private List<String> profilePhoto;
    private List<String> lastname;
    private List<String> name;
    private List<String> username;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private List<Long> date;
    private List<Double> latitude;
    private List<Double> longitude;
    private List<String> userPhoneNumbers;
}
