package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.constants.PinRange;
import ru.personal.models.User;
import ru.personal.security.role.Role;
import ru.personal.services.interfaces.UserService;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> login(@RequestParam String phoneNumber){

        long pin = ThreadLocalRandom.current().nextInt(PinRange.MIN, PinRange.MAX + 1);
        System.out.println("pincode is "+pin);
        User user = User.builder()
                .phoneNumber(phoneNumber)
                .pin(pin)
                .role(Role.USER)
                .build();
        userService.saveUser(user);
        return ResponseEntity.ok("{\"pincode\":\""+pin+"\"}");
    }
}
