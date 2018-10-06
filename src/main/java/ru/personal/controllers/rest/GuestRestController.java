package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.dto.UserProfileDTO;
import ru.personal.services.interfaces.ControlAccessService;

/**
 * Date 26.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/guest")
public class GuestRestController {


    @Autowired
    private ControlAccessService controlAccessService;

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getUserProfile(@RequestParam String token,
                                            @PathVariable("username") String username){
        UserProfileDTO userProfileDTO = controlAccessService.getUserProfile(username, token);
        return ResponseEntity.ok(userProfileDTO);

    }

    @PostMapping("/req")
    public ResponseEntity requestUser(@RequestParam String token,
                                      @RequestParam String username){
        controlAccessService.addNewRequestUser(username, token);
        return ResponseEntity.ok().build();
    }
}
