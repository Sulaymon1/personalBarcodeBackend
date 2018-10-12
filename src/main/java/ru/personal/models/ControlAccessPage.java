package ru.personal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Date 26.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Entity
@Table(name = "control_access_page")
@EqualsAndHashCode(exclude = {"friends", "usersRequest"})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ControlAccessPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private Boolean isClosed;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> friends;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> usersRequest;

}

