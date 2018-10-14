package ru.personal.services.interfaces;

/**
 * Date 14.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface LocationService {
    void switchLocation(String token, Boolean status);

    void setLocation(String token, String longitude, String attitude);
}
