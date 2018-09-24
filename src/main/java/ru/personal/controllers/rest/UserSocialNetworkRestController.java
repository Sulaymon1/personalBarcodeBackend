package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.personal.dto.SocialNetworkData;
import ru.personal.models.User;
import ru.personal.services.interfaces.SocialNetworkService;
import ru.personal.services.interfaces.UserService;

/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/user/social")
public class UserSocialNetworkRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private SocialNetworkService socialNetworkService;

    @PostMapping("/add")
    public ResponseEntity addNewSocialNetworkProfile(@RequestParam String token, @RequestParam String profileId,@RequestParam String socialType){
        User user = userService.getUserByToken(token);
        socialNetworkService.add(user, profileId, socialType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get")
    public ResponseEntity<SocialNetworkData> getStatusInfo(@RequestParam String token){
        User user = userService.getUserByToken(token);
        return ResponseEntity.ok( socialNetworkService.getInfo(user));
    }
}
