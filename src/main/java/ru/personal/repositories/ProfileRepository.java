package ru.personal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.personal.models.Profile;

/**
 * Date 29.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface ProfileRepository extends JpaRepository<Profile, Long>{
}