package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.dto.UserDTO;
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

    @PostMapping("/deleteFriend")
    public ResponseEntity deleteFollower(@RequestParam String token,
                                         @RequestParam String username) throws Exception {
        controlAccessService.deleteFollower(username, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getRequestedUsers")
    public ResponseEntity<UserDTO> getRequestedUsers(@RequestParam String token){
        UserDTO userDTO = controlAccessService.getRequestedUsers(token);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/getFriends")
    public ResponseEntity<UserDTO> getFollowers(@RequestParam String token){
        UserDTO userDTO = controlAccessService.getFriends(token);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/privateProfile")
    public ResponseEntity privateProfile(@RequestParam String token, @RequestParam Boolean status){
        controlAccessService.closeProfile(token, status);
        return ResponseEntity.ok().build();
    }

}
