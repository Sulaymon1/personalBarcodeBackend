package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.models.User;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.FileInfoService;
import ru.personal.services.interfaces.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/get")
    public ResponseEntity<User> getUser(@RequestParam String token){
        return ResponseEntity.ok(userService.getUserByToken(token));
    }

    @PostMapping(value = "/updateUser")
    public ResponseEntity updateUsername(@RequestParam String token, @RequestParam(required = false) String username,
                                         @RequestParam(required = false) String name, @RequestParam(required = false)String imageBytes){
        userService.updateUser(token, username,name, imageBytes);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/photo/{fileName:.+}")
    public void getPhoto(@PathVariable("fileName") String fileName, HttpServletResponse response){
        fileInfoService.getPicture(fileName, response);
    }

    @PostMapping("/checkUsername")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String token,
                                                 @RequestParam String username){
        User user = jwtTokenUtil.getUserFromToken(token);
        if (user != null) {
            boolean hasUsername = userService.hasUsername(username);
            return ResponseEntity.ok(Collections.singletonMap("answer", hasUsername));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateUsername")
    public ResponseEntity<Map<String, Boolean>> saveUsername(@RequestParam String token,
                                                             @RequestParam String username, @RequestParam String qrImage){
        return ResponseEntity.ok(Collections.singletonMap("answer", userService.updateUsername(token, username, qrImage)));
    }
}

// eyJhbGciOiJIUzUxMiJ9.eyJwaG9uZU51bWJlciI6IjEyMyIsInJvbGUiOiJVU0VSIiwic3ViIjoiMSIsImlhdCI6MTUzNzI5MzgwOSwiZXhwIjoxNTM3ODk4NjA5fQ.grt0tBofTq_MxhlzdSPAQ6UYJu0AOKwxsBZFRE4_ViaaHXxngCn2j4sRKYHBRV8VO_dxDvDw_l_s6NPRzxE4xA
