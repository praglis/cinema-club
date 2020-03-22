package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.serviceInterface.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/ban")
    public ResponseEntity banUser(@RequestParam(value = "userName") String userName) {
        return adminService.banUser(userName);
    }

    @GetMapping("/getUsers")
    public List<String> getUsers(){
        return adminService.getAllUsers();
    }
}
