package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.personal.services.interfaces.LocationService;

/**
 * Date 14.10.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/location")
public class LocationRestController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/switch")
    public ResponseEntity switchLocation(@RequestParam String token, @RequestParam Boolean status){
        locationService.switchLocation(token, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/set")
    public ResponseEntity setLocation(@RequestParam String token,
                                      @RequestParam String longitude,
                                      @RequestParam String attitude){
        locationService.setLocation(token, longitude, attitude);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get")
    public ResponseEntity getLocation(){
        return null;
    }
}
