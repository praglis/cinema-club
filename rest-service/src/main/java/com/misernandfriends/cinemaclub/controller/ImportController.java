package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.serviceInterface.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("import")
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping("/loadTestPreferences")
    public ResponseEntity loadTestPreferences(@RequestParam(value = "file") String filePath) throws IOException {
        importService.importPreferences(new File(filePath), new File(new File(filePath).getParent(), "user"));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/loadTestUsers")
    public ResponseEntity loadTestUsers(@RequestParam(value = "file") String filePath) throws IOException {
        importService.importUsers(new File(filePath));
        return ResponseEntity.noContent().build();
    }
}
