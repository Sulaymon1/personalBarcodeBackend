package ru.personal.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Date 06.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "advertisements")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String adName;
    private String adDescription;
    private String adLink;
    private String adPhotoLink;
}
