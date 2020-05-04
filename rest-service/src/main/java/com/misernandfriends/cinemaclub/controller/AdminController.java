package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.serviceInterface.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/block")
    public ResponseEntity blockUser(@RequestParam(value = "userName") String userName) {
        return adminService.blockUser(userName);
    }

    @PutMapping("/active")
    public ResponseEntity activeUser(@RequestParam(value = "userName") String userName) {
        return adminService.activeUser(userName);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam(value = "userName") String userName) {
        return adminService.deleteUser(userName);
    }

    @PutMapping("/highlight")
    public ResponseEntity highlightUserReview(@RequestParam(value = "id") Long id) {
        return adminService.highlightUserReview(id);
    }
}
