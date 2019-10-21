package com.misernandfriends.cinemaclub.controllers;

import com.misernandfriends.cinemaclub.entites.Greeting;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/")
@RestController
public class TestController {

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", required = false) String name) {
        return new Greeting(0, name == null ? "Bezi" : name);
    }

}
