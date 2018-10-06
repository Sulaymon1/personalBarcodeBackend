package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.models.User;
import ru.personal.services.interfaces.ControlAccessService;

import java.util.List;

/**
 * Date 26.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/user/access")
public class AccessControlRestController {
    // accept guest // ignore guest // delete user // list of requests

    @Autowired
    private ControlAccessService controlAccessService;

    @PostMapping("/accept")
    public ResponseEntity accept(@RequestParam String token,
                                 @RequestParam String username) throws Exception {
        controlAccessService.accept(username, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decline")
    public ResponseEntity decline(@RequestParam String token,
                                  @RequestParam String username) throws Exception {
        controlAccessService.decline(username, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteFollower")
    public ResponseEntity deleteFollower(@RequestParam String token,
                                         @RequestParam String username) throws Exception {
        controlAccessService.deleteFollower(username, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getRequestedUsers")
    public ResponseEntity<List<User>> getRequestedUsers(@RequestParam String token){
        List<User> users = controlAccessService.getRequestedUsers(token);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/getFollowers")
    public ResponseEntity<List<User>> getFollowers(@RequestParam String token){
        List<User> users = controlAccessService.getFollowers(token);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/privateProfile")
    public ResponseEntity privateProfile(@RequestParam String token, @RequestParam Boolean status){
        controlAccessService.closeProfile(token, status);
        return ResponseEntity.ok().build();
    }

}
