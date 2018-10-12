package ru.personal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Date 12.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@Builder
public class GuestDto {
    private String profilePhoto;
    private String lastname;
    private String name;
    private String username;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDateTime date;
}
