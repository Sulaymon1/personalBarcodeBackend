package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.constants.Image;
import ru.personal.form.UserForm;
import ru.personal.models.Token;
import ru.personal.models.User;
import ru.personal.services.interfaces.AuthenticationService;
import ru.personal.services.interfaces.FileInfoService;
import ru.personal.services.interfaces.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileInfoService fileInfoService;

    // TODO: 19.09.2018 write it correctly
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestParam(name = "phone") String phone, @RequestParam("pincode")String pincode){
        UserForm userForm = new UserForm();
        userForm.setPhone(phone);
        userForm.setPincode(pincode);
        Map<String, Object> map = new HashMap<>();
        Token token = authenticationService.login(userForm);
        User user = userService.getUserByToken(token.getToken());
        map.put("token", token.getToken());
        map.put("username", user.getUsername());
        map.put("qrImage",fileInfoService.getImageBase64(user.getProfilePhotoPath(), Image.QRimage));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}

