package ru.personal.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Date 04.07.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "Test passed successfully";
    }

    @GetMapping("/authTest")
    public String authTest(){
        return "Auth test passed successfully";
    }
}
