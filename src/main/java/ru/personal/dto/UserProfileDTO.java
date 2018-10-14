package ru.personal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.personal.models.Advertisement;
import ru.personal.models.SocialNetwork;

import java.time.LocalDate;

/**
 * Date 26.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDTO {

    private String phoneNumber;

    private String username;
    private String name;
    private String lastName;

    private String country;
    private String city;
    private String bCountry;
    private String bCity;
    private String bExtra;

    private Advertisement advertisement;
    private Long status;
    private String withUsername;

    private String profilePhotoPath;
    private String qrImagePath;
    private String coverPhotoPath;

    private SocialNetwork socialNetwork;
    private Boolean isRequested;

    private Boolean isClosed;

}
