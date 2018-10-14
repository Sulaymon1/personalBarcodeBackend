package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.models.Location;
import ru.personal.models.User;
import ru.personal.repositories.LocationRepository;
import ru.personal.repositories.UserRepository;
import ru.personal.services.interfaces.LocationService;
import ru.personal.services.interfaces.UserService;

/**
 * Date 14.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;


    @Override
    public void switchLocation(String token, Boolean status) {
        User user = userService.getUserByToken(token);
        user.setLocationStatus(status);
        userRepository.save(user);
    }

    @Override
    public void setLocation(String token, String longitude, String attitude) {
        User user = userService.getUserByToken(token);
        Location location = Location.builder()
                .user(user)
                .longitude(longitude)
                .attitude(attitude)
                .build();
        locationRepository.save(location);
    }
}
