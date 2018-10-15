package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.dto.LocationDTO;
import ru.personal.models.Location;
import ru.personal.models.User;
import ru.personal.repositories.LocationRepository;
import ru.personal.repositories.UserRepository;
import ru.personal.services.interfaces.LocationService;
import ru.personal.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

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
        Location location = locationRepository.findFirstByUser(user);
        if (location == null){
            location = Location.builder()
                    .user(user)
                    .locationStatus(true)
                    .build();
        }
        location.setLocationStatus(status);
        locationRepository.save(location);
    }

    @Override
    public void setLocation(String token, String longitude, String attitude) {
        User user = userService.getUserByToken(token);
        Location location = locationRepository.findFirstByUser(user);
        if (location == null) {
            location = Location.builder()
                    .user(user)
                    .longitude(longitude)
                    .attitude(attitude)
                    .build();
        }
        location.setAttitude(attitude);
        location.setLongitude(longitude);
        locationRepository.save(location);
    }

    @Override
    public LocationDTO getLocations(String attitude, String longitude){
        List<Location> list =
                locationRepository.findAllByLocationStatusIsTrueAndAttitudeStartsWithAndLongitudeStartsWith(attitude, longitude);
        LocationDTO locationDTO = LocationDTO.builder()
                .username(new ArrayList<>())
                .lastName(new ArrayList<>())
                .name(new ArrayList<>())
                .profilePhotoPath(new ArrayList<>())
                .longitude(new ArrayList<>())
                .attitude(new ArrayList<>())
                .build();
        for (Location location: list){
            locationDTO.getUsername().add(location.getUser().getUsername());
            locationDTO.getLastName().add(location.getUser().getLastName());
            locationDTO.getName().add(location.getUser().getName());
            locationDTO.getProfilePhotoPath().add(location.getUser().getProfilePhotoPath());
            locationDTO.getLongitude().add(location.getLongitude());
            locationDTO.getAttitude().add(location.getAttitude());
        }

        return locationDTO;
    }
}
