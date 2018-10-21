package ru.personal.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Date 21.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@EqualsAndHashCode(exclude = {"admin","members"})
@Entity
@Table(name = "groups")
@ToString(exclude = {"admin", "members"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Long groupID;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User admin;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> members;

    private String groupPhoto;
    private Double longitude; // setting meeting
    private Double latitude;  //  location
    private Long meetingTime; // unixtime
}
