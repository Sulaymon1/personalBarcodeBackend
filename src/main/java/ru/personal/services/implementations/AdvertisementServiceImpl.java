package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.constants.Image;
import ru.personal.form.AdwordForm;
import ru.personal.models.Advertisement;
import ru.personal.models.User;
import ru.personal.repositories.AdvertisementRepository;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.AdvertisementService;
import ru.personal.services.interfaces.FileInfoService;

import java.util.Optional;

/**
 * Date 06.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void addNewAdvertisement(AdwordForm adwordForm) {
        User user = jwtTokenUtil.getUserFromToken(adwordForm.getToken());
        Optional<Advertisement> advertisementOptional = advertisementRepository.findFirstByUserId(user.getId());
        String picName = fileInfoService.savePicture(adwordForm.getAdPhoto(), Image.AdvertisementPic);
        Advertisement advertisement = advertisementOptional.orElse(new Advertisement());
        advertisement.setUserId(user.getId());
        advertisement.setAdDescription(adwordForm.getAdDescription());
        advertisement.setAdLink(adwordForm.getAdLink());
        advertisement.setAdName(adwordForm.getAdName());
        advertisement.setAdPhotoLink(picName);
        advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement getAdvertisement(String token) {
        User user = jwtTokenUtil.getUserFromToken(token);
        return advertisementRepository.findFirstByUserId(user.getId()).orElse(null);
    }

    @Override
    public void deleteAdvertisement(String token) {
        User user = jwtTokenUtil.getUserFromToken(token);
        advertisementRepository.deleteByUserId(user.getId());
    }
}
