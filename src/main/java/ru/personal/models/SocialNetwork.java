package ru.personal.models;

import lombok.*;

import javax.persistence.*;

/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Entity
@Table(name = "social_network")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String socialType;
    private String socialId;
}
