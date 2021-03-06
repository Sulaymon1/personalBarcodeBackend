package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.form.AdwordForm;
import ru.personal.services.interfaces.AdvertisementService;

/**
 * Date 06.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestParam String adName, @RequestParam String token,
                              @RequestParam String adDescription, @RequestParam String adLink,
                              @RequestParam String adPhoto){
        AdwordForm adwordForm = new AdwordForm();
        adwordForm.setAdDescription(adDescription);
        adwordForm.setAdLink(adLink);
        adwordForm.setAdName(adName);
        adwordForm.setAdPhoto(adPhoto);
        adwordForm.setToken(token);
        advertisementService.addNewAdvertisement(adwordForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestParam String token){
       return ResponseEntity.ok(advertisementService.getAdvertisement(token));
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestParam String token){
        advertisementService.deleteAdvertisement(token);
        return ResponseEntity.ok().build();
    }
}
