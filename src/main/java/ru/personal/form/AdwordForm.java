package ru.personal.form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Date 06.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
public class AdwordForm {
    private String token;

    @NotNull
    private String adName;
    @NotNull
    private String adDescription;
    @NotNull
    private String adLink;
    @Transient
    @NotNull
    private String adPhoto;

}
