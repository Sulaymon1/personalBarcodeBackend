package ru.personal.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.personal.form.UserForm;
import ru.personal.models.Token;
import ru.personal.services.interfaces.AuthenticationService;


@RestController
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<Token> login(@RequestParam(name = "phone") String phone, @RequestParam("pincode")String pincode){
        UserForm userForm = new UserForm();
        userForm.setPhone(phone);
        userForm.setPincode(pincode);
        return ResponseEntity.ok(authenticationService.login(userForm));
    }


}

