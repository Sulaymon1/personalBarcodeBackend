package ru.personal.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Date 21.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@ToString
public class GroupForm {
    private String title;
    private List<String> username;
    private String image;
    private String token;
}
