package ru.personal.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
public class UserProfileDTO {

    private String phoneNumber;

    private String username;
    private String name;
    private String lastName;

    private String profilePhotoPath;
    private String qrImagePath;
    private String coverPhotoPath;

    private LocalDate birthday;

    private SocialNetwork socialNetwork;
    private Boolean isRequested;
}
