package ru.personal.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Date 26.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/user")
public class AccessControlRestController {
    // accept guest // ignore guest // delete user // list of requests
    @PostMapping("/accept")
    public ResponseEntity accept(@RequestParam String token,
                                 @RequestParam String username){
        return null;
    }
}
