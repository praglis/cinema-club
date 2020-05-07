package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.serviceInterface.user.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/ban")
    public ResponseEntity<Object> banUser(@RequestParam(value = "userName") String userName) {
        return adminService.banUser(userName);
    }

    @PutMapping("/block")
    public ResponseEntity<Object> blockUser(@RequestParam(value = "userName") String userName) {
        return adminService.blockUser(userName);
    }

    @PutMapping("/active")
    public ResponseEntity<Object> activeUser(@RequestParam(value = "userName") String userName) {
        return adminService.activeUser(userName);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestParam(value = "userName") String userName) {
        return adminService.deleteUser(userName);
    }
}
