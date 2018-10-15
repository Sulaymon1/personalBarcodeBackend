package ru.personal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.personal.models.Location;
import ru.personal.models.User;

import java.util.List;

/**
 * Date 14.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByLocationStatusIsTrueAndAttitudeStartsWithAndLongitudeStartsWith(String a, String b);
    Location findFirstByUser(User user);
}
