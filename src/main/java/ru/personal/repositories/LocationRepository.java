package ru.personal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query(nativeQuery = true, value = "SELECT * FROM locations a " +
            "WHERE a.location_status=TRUE AND a.latitude >= :ilatitude AND a.latitude<= :flatitude " +
            "and a.longitude >= :ilongitude and a.longitude <= :flongitude")
    List<Location> findAllByLocationStatusIsTrueAndAttitudeStartsWithAndLongitudeStartsWith(
            @Param("ilatitude") Float ilatitude,@Param("ilongitude") Float ilongitude,
            @Param("flatitude") Float flatitude,@Param("flongitude") Float flongitude);
    Location findFirstByUser(User user);
}
