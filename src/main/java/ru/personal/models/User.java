package ru.personal.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.personal.security.role.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "users")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String username;
    private String name;
    private String lastName;
    private Long pin;
    private String profilePhotoPath;
    private String qrImagePath;
    private String coverPhotoPath;
    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate birthday;

    private String country;
    private String city;
    private String bCountry;
    private String bCity;
    private String bExtra;

    private Long status;
    private String withUsername;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private SocialNetwork socialNetwork;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ControlAccessPage controlAccessPage;
}
