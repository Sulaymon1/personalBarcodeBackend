package ru.personal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.personal.models.Guest;

/**
 * Date 11.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
