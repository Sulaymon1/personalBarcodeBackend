package ru.personal.services.interfaces;

import ru.personal.dto.LocationDTO;

/**
 * Date 14.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface LocationService {
    void switchLocation(String token, Boolean status);

    void setLocation(String token, Double longitude, Double latitude);

    LocationDTO getLocations(Double iLat, Double iLong, Double fLat, Double fLong);
}
