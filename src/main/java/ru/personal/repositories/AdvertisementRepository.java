package ru.personal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.personal.models.Advertisement;

import java.util.Optional;

/**
 * Date 06.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Optional<Advertisement> findFirstByUserId(Long userID);
    void deleteByUserId(Long userID);
}
