package ru.personal.services.interfaces;

import ru.personal.form.AdwordForm;
import ru.personal.models.Advertisement;

/**
 * Date 06.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface AdvertisementService {
    void addNewAdvertisement(AdwordForm adwordForm);
    Advertisement getAdvertisement(String token);
    void deleteAdvertisement(String token);
}
