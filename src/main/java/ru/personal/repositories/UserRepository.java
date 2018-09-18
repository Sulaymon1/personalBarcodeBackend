package ru.personal.repositories;

import ru.personal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByPhoneNumber(String phoneNumber);
    Optional<User> findUserByPhoneNumberAndPin(String phone, Long pin);
    Optional<User> findUserByUsername(String username);
}
