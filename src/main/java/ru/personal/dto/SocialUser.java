package ru.personal.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Setter
@Getter
public class SocialUser {
    private List<String> socialType = new ArrayList<>();
    private List<String> id = new ArrayList<>();
}
