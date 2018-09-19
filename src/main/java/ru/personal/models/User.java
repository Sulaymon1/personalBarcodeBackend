package ru.personal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.personal.security.role.Role;

import javax.persistence.*;
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
    private String qrImagePath;
    private String name;
    private Long pin;
    private String picName;


    @Enumerated(EnumType.STRING)
    private Role role;
}
