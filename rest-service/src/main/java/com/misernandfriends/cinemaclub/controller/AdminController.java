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
        adminService.banUser(userName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/block")
    public ResponseEntity<Object> blockUser(@RequestParam(value = "userName") String userName) {
        adminService.blockUser(userName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active")
    public ResponseEntity<Object> activeUser(@RequestParam(value = "userName") String userName) {
        adminService.activeUser(userName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestParam(value = "userName") String userName) {
        adminService.deleteUser(userName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/highlight/{commentId}")
    public ResponseEntity<Object> highlightUserReview(@PathVariable Long commentId) {
        adminService.highlightUserReview(commentId);
        return ResponseEntity.ok().build();
    }
}
