package ru.personal.models;

import lombok.*;

import javax.persistence.*;

/**
 * Date 18.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/

@Getter
@Setter
@Entity
@Table(name = "profile")
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



}
