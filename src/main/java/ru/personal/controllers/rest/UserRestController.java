package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.models.User;
import ru.personal.services.interfaces.FileInfoService;
import ru.personal.services.interfaces.UserService;

import javax.servlet.http.HttpServletResponse;

/**
 * Date 03.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileInfoService fileInfoService;

    @PostMapping(value = "/get")
    public ResponseEntity<User> getUser(@RequestParam String token){
        return ResponseEntity.ok(userService.getUserByToken(token));
    }

    @PostMapping(value = "/updateUser")
    public ResponseEntity updateUsername(@RequestParam String token, @RequestParam(required = false) String username,
                                         @RequestParam(required = false) String name, @RequestParam(required = false)String imageBytes){
        userService.updateUsername(token, username,name, imageBytes);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/photo/{fileName:.+}")
    public void getPhoto(@PathVariable("fileName") String fileName, HttpServletResponse response){
        fileInfoService.getPicture(fileName, response);
    }

}
