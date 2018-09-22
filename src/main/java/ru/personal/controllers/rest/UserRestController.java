package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.constants.Image;
import ru.personal.models.User;
import ru.personal.security.JwtTokenUtil;
import ru.personal.services.interfaces.FileInfoService;
import ru.personal.services.interfaces.UserService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
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



    @PostMapping("/photo/{fileName:.+}")
    public void getPhoto(@PathVariable("fileName") String fileName, HttpServletResponse response){
        fileInfoService.getProfilePhoto(fileName, response);
    }

    @PostMapping("/qrImage/{fileName:.+}")
    public ResponseEntity<?> getQRImage(@PathVariable("fileName") String fileName){
        String imageBase64 = fileInfoService.getImageBase64(fileName, Image.QRimage);
        Map<String, String> map = new HashMap<>();
        map.put("qrImage",imageBase64);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/uploadPhoto")
    public ResponseEntity uploadPhoto(@RequestParam String token,
                                      @RequestParam String profilePhotoBase64){
        User user = userService.getUserByToken(token);
        if (user != null){
            String pictureName = fileInfoService.savePicture(profilePhotoBase64, Image.Photo);
            user.setProfilePhotoPath(pictureName);
            userService.saveUser(user);
        }
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Map<String, Boolean>> updateUsername(@RequestParam String token,
                                                             @RequestParam String username, @RequestParam String qrImage){
        return ResponseEntity.ok(Collections.singletonMap("answer", userService.updateUsername(token, username, qrImage)));
    }

    @PostMapping("/updateUserInfo")
    public ResponseEntity<?> updateUserInfo(@RequestParam String token,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String lastname, // 2018-09-22
                                             @RequestParam(required = false) @DateTimeFormat( iso = DateTimeFormat.ISO.DATE) LocalDate date){
        userService.updateUserInfo(token, name, lastname, date);
        return ResponseEntity.ok().build();

    }


}

